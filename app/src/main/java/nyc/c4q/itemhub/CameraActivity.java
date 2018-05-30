package nyc.c4q.itemhub;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nyc.c4q.itemhub.CameraUtils.CameraSource;
import nyc.c4q.itemhub.CameraUtils.CameraSourcePreview;
import nyc.c4q.itemhub.CameraUtils.GraphicOverlay;
import nyc.c4q.itemhub.barcodescanning.BarcodeScanningProcessor;

public class CameraActivity extends AppCompatActivity implements ScanResult {

    private static final String TAG = "CameraActivity";
    private static final String CAMERA = "Camera";
    private static final int PERMISSION_REQUESTS = 1;

    private static final String BARCODE_DETECTION = "Barcode Detection";
    private CameraSource cameraSource = null;
    private CameraSourcePreview preview;
    private GraphicOverlay graphicOverlay;
    private String selectedModel = BARCODE_DETECTION;
    private BarcodeScanningProcessor barcodeScanningProcessor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);

        preview = (CameraSourcePreview) findViewById(R.id.firePreview);
        if (preview == null) {
            Log.d(TAG, "Preview is null");
        }
        graphicOverlay = (GraphicOverlay) findViewById(R.id.fireFaceOverlay);
        if (graphicOverlay == null) {
            Log.d(TAG, "graphicOverlay is null");
        }

        barcodeScanningProcessor= new BarcodeScanningProcessor(this);
        
        if (allPermissionsGranted()) {
            createCameraSource(selectedModel);
        } else {
            getRuntimePermissions();
        }
    }


    private void startCameraSource() {
        if (cameraSource != null) {
            try {
                if (preview == null) {
                    Log.d(TAG, "resume: Preview is null");
                }
                if (graphicOverlay == null) {
                    Log.d(TAG, "resume: graphOverlay is null");
                }
                preview.start(cameraSource, graphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                cameraSource.release();
                cameraSource = null;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        preview.stop();
        Log.d(TAG, "onPause: ==== I am ON PAUSE");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
        }
        Log.d(TAG, "onDestroy: ===== I am ON DESTROY");
    }

    private String[] getRequiredPermissions() {
        try {
            PackageInfo info =
                    this.getPackageManager()
                            .getPackageInfo(this.getPackageName(), PackageManager.GET_PERMISSIONS);
            String[] ps = info.requestedPermissions;
            if (ps != null && ps.length > 0) {
                return ps;
            } else {
                return new String[0];
            }
        } catch (Exception e) {
            return new String[0];
        }
    }

    private boolean allPermissionsGranted() {
        for (String permission : getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                return false;
            }
        }
        return true;
    }

    private void getRuntimePermissions() {
        List <String> allNeededPermissions = new ArrayList <>();
        for (String permission : getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                allNeededPermissions.add(permission);
            }
        }

        if (!allNeededPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(
                    this, allNeededPermissions.toArray(new String[0]), PERMISSION_REQUESTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        Log.i(TAG, "Permission granted!");
        if (allPermissionsGranted()) {
            createCameraSource(selectedModel);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private static boolean isPermissionGranted(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission granted: " + permission);
            return true;
        }
        Log.i(TAG, "Permission NOT granted: " + permission);
        return false;
    }

    private void createCameraSource(String model) {
        // If there's no existing cameraSource, create one.
        if (cameraSource == null) {
            cameraSource = new CameraSource(this, graphicOverlay);
            Log.d(TAG, "createCameraSource: ======== new camera source");
        }

        switch (model) {
            case BARCODE_DETECTION:
                Log.i(TAG, "Using Barcode Detector Processor");
                cameraSource.setMachineLearningFrameProcessor(barcodeScanningProcessor);
                break;
            default:
                Log.e(TAG, "Unknown model: " + model);
        }
    }

    @Override
    public void getBarcodeResult(long barcode) {
        Log.d(TAG, "getBarcodeResult: " + barcode);
        intentToResultActivity(barcode);
    }

    private void intentToResultActivity(long barcodeNumber) {
        Intent intent = new Intent(CameraActivity.this, ProductResultActivity.class);
        intent.putExtra("barcodeNumber", barcodeNumber);
        startActivity(intent);
    }


}