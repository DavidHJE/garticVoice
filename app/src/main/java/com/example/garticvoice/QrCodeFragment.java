package com.example.garticvoice;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.garticvoice.databinding.FragmentFirstBinding;
import com.example.garticvoice.databinding.FragmentQrCodeBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QrCodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QrCodeFragment extends Fragment {
    public static final String ARG_GAME_ID = "game_id";
    private FragmentQrCodeBinding binding;

    private String gameId;
    private ImageView qrcodeImg;

    public QrCodeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param gameId id of the created game.
     * @return A new instance of fragment QrCodeFragment.
     */
    public static QrCodeFragment newInstance(String gameId) {
        QrCodeFragment fragment = new QrCodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GAME_ID, gameId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() == null) {
            Log.d("BARCODE", "Arguments non trouvé");
            return;
        }

        gameId = getArguments().getString(ARG_GAME_ID);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentQrCodeBinding.inflate(inflater, container, false);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qr_code, container, false);
    }

    private Bitmap createQRCode(String gameId) throws WriterException {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        return barcodeEncoder.encodeBitmap(gameId, BarcodeFormat.QR_CODE, 400, 400);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        qrcodeImg = view.findViewById(R.id.QRCodeImg);

        if(TextUtils.isEmpty(gameId)) {
            Log.d("BARCODE", "Gameid Empty");
            Toast.makeText(getContext(), "Game non creé", Toast.LENGTH_SHORT).show();
        } else {
            try {
                Bitmap qrCode = createQRCode(gameId);
                qrcodeImg.setImageBitmap(qrCode);
                Toast.makeText(getContext(), "Creer QRCode", Toast.LENGTH_SHORT).show();
            } catch(Exception e) {
                Log.d("BARCODE - ERROR", e.toString());
            }
        }
    }
}