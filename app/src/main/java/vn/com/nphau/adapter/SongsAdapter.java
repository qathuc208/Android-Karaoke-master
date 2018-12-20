package vn.com.nphau.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import vn.com.nphau.karaoke.R;
import vn.com.nphau.model.Song;

/**
 * Created by nphau on 8/28/2015.
 */
public class SongsAdapter extends ArrayAdapter<Song> {

    public static int selectedTab = 0;

    private static class ViewHolger {
        TextView txtName, txtId, txtAuthor;

        ImageButton btnFavorite;
    }

    public SongsAdapter(Activity context, List<Song> objects)
    {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Song song = getItem(position);

        // view lookup cache stored in tag
        ViewHolger viewHolger;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {

            viewHolger = new ViewHolger();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_row, parent, false);

            // Get controls
            viewHolger.txtId = (TextView) convertView.findViewById(R.id.txt_id);
            viewHolger.txtName = (TextView) convertView.findViewById(R.id.txt_name);
            viewHolger.txtAuthor = (TextView) convertView.findViewById(R.id.txt_author);
            viewHolger.btnFavorite = (ImageButton) convertView.findViewById(R.id.btn_favorite);

            convertView.setTag(viewHolger);

        } else
        {
            viewHolger = (ViewHolger) convertView.getTag();
        }

        // Lookup view for data population
        viewHolger.txtId.setText(song.getId());
        viewHolger.txtName.setText(song.getName());
        viewHolger.txtAuthor.setText(song.getAuthor());

        viewHolger.btnFavorite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                setFavorite();

            }
        });

        return convertView;
    }

    private void setFavorite() {

        // Intent intent = new Intent (getContext(), AlarmActivity.class);

    }
}
