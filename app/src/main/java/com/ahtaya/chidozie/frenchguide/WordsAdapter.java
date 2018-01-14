package com.ahtaya.chidozie.frenchguide;

        import android.app.Activity;
        import android.graphics.drawable.GradientDrawable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toolbar;

        import java.util.ArrayList;

public class WordsAdapter extends ArrayAdapter<Words> {

    private int color;

    public WordsAdapter(Activity context, ArrayList<Words> numWords, int col) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, numWords);
        color = col;
    }
    //public static int color;
    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.my_list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Words aWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = listItemView.findViewById(R.id.en_view);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        nameTextView.setText(aWord.getEnWord());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView numberTextView = listItemView.findViewById(R.id.fr_view);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        numberTextView.setText(aWord.getFrWord());

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        ImageView iconView = listItemView.findViewById(R.id.img_view);
        // Get the image resource ID from the current AndroidFlavor object and
        // set the image to iconView
        if (aWord.getImgId() != 0) {
            iconView.setImageResource(aWord.getImgId());
        }else if(aWord.getmColorId() != 0){
            iconView.setBackgroundResource(R.drawable.round_shape);
            GradientDrawable backgroundGradient = (GradientDrawable)iconView.getBackground();
            backgroundGradient.setColor(getContext().getResources().getColor(aWord.getmColorId()));
        }else {
            iconView.setVisibility(View.GONE);
        }

        //ImageView playButton = listItemView.findViewById(R.id.play_button);
        //playButton.setBackgroundColor(getContext().getResources().getColor(color));

        RelativeLayout relativeLayout = listItemView.findViewById(R.id.my_list);
        relativeLayout.setBackgroundColor(getContext().getResources().getColor(color));

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }

}