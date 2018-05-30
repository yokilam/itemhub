// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package nyc.c4q.itemhub.barcodescanning;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;


import java.io.IOException;
import java.util.List;

import nyc.c4q.itemhub.CameraUtils.FrameMetadata;
import nyc.c4q.itemhub.CameraUtils.GraphicOverlay;
import nyc.c4q.itemhub.ScanResult;
import nyc.c4q.itemhub.CameraUtils.VisionProcessorBase;

public class BarcodeScanningProcessor extends VisionProcessorBase <List <FirebaseVisionBarcode>> {

    private static final String TAG = "ScanProc";
    private static final String BARCODE = "Barcode";
    private static long barcodeNumber;
    ScanResult scanResultListener;

    private final FirebaseVisionBarcodeDetector detector;

    public BarcodeScanningProcessor(ScanResult scanResultListener) {
        detector = FirebaseVision.getInstance().getVisionBarcodeDetector();
        this.scanResultListener=scanResultListener;
    }

    @Override
    public void stop() {
        try {
            detector.close();
        } catch (IOException e) {
            Log.e(TAG, "Exception thrown while trying to close Barcode Detector: " + e);
        }
    }

    @Override
    protected Task <List <FirebaseVisionBarcode>> detectInImage(FirebaseVisionImage image) {
        return detector.detectInImage(image);
    }

    @Override
    protected void onSuccess(
            @NonNull List <FirebaseVisionBarcode> barcodes,
            @NonNull FrameMetadata frameMetadata,
            @NonNull GraphicOverlay graphicOverlay) {
        graphicOverlay.clear();
        for (int i = 0; i < barcodes.size(); ++i) {
            FirebaseVisionBarcode barcode = barcodes.get(i);
            BarcodeGraphic barcodeGraphic = new BarcodeGraphic(graphicOverlay, barcode);
            graphicOverlay.add(barcodeGraphic);
            Log.d(BARCODE, "barcode: " + barcode.getRawValue());
            barcodeNumber= Long.parseLong(barcode.getRawValue());
            scanResultListener.getBarcodeResult(barcodeNumber);
        }
    }

    @Override
    protected void onFailure(@NonNull Exception e) {
        Log.e(TAG, "Barcode detection failed " + e);
    }

    public static long getBarcodeNumber() {
        return barcodeNumber;
    }
}
