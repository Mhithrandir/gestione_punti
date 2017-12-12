package ghimdalasgmail_com.gestionepuntinippon;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class numericUpDown extends Fragment {
    private String _numero;
    public numericUpDown() {
    }
    public static numericUpDown newInstance() {
        numericUpDown fragment = new numericUpDown();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_numeric_up_down, container, false);
    }
}
