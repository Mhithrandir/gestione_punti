package ghimdalasgmail_com.gestionepuntinippon;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentCancellazionePunti extends Fragment {
    public FragmentCancellazionePunti(){

    }
    public static FragmentCancellazionePunti newInstance() {
        FragmentCancellazionePunti fragment = new FragmentCancellazionePunti();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment_cancellazione_punti, container, false);
    }
}
