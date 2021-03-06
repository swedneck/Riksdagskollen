package oscar.riksdagskollen.Util.Adapter;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import oscar.riksdagskollen.R;
import oscar.riksdagskollen.RikdagskollenApp;
import oscar.riksdagskollen.Util.JSONModel.CurrentNews;

/**
 * Created by oscar on 2018-03-29.
 */

public class CurrentNewsListAdapter  extends RiksdagenViewHolderAdapter{
    private final SortedList<CurrentNews> newsList = new SortedList<>(CurrentNews.class, new SortedList.Callback<CurrentNews>() {
        @Override
        public int compare(CurrentNews o1, CurrentNews o2) {
            return mComparator.compare(o1, o2);
        }

        @Override
        public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(CurrentNews oldItem, CurrentNews newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(CurrentNews item1, CurrentNews item2) {
            return item1.equals(item2);
        }

        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }
    });


    private static final Comparator<CurrentNews> DEFAULT_COMPARATOR = new Comparator<CurrentNews>() {
        @Override
        public int compare(CurrentNews a, CurrentNews b) {
            return 0;
        }
    };
    private Comparator<CurrentNews> mComparator = DEFAULT_COMPARATOR;

    public CurrentNewsListAdapter(List<CurrentNews> items, final OnItemClickListener listener) {
        super(listener);
        setSortedList(newsList);
        addAll(items);
        this.clickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_list_row, parent, false);
            return new CurrentNewsListAdapter.NewsViewHolder(itemView);
        } else {
            FrameLayout frameLayout = new FrameLayout(parent.getContext());
            //make sure it fills the space
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new HeaderFooterViewHolder(frameLayout);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position < headers.size()){
            View v = headers.get(position);
            //add our view to a header view and display it
            prepareHeaderFooter((HeaderFooterViewHolder) holder, v);
        }else if(position >= headers.size() + newsList.size()){
            View v = footers.get(position-newsList.size()-headers.size());
            //add our view to a footer view and display it
            prepareHeaderFooter((HeaderFooterViewHolder) holder, v);
        }else {
            CurrentNews document = newsList.get(position);
            ((CurrentNewsListAdapter.NewsViewHolder) holder).bind(document, this.clickListener);
        }
    }

    public void add(CurrentNews item) {
        newsList.add(item);
    }

    public void remove(CurrentNews item) {
        newsList.remove(item);
    }

    @Override
    public void addAll(List<?> items) {
        newsList.addAll((Collection<CurrentNews>) items);
    }

    @Override
    public void removeAll(List<?> items) {
        newsList.beginBatchedUpdates();
        for (Object item : items) {
            newsList.remove((CurrentNews) item);
        }
        newsList.endBatchedUpdates();
    }

    @Override
    public void replaceAll(List<?> items) {
        newsList.beginBatchedUpdates();
        for (int i = newsList.size() - 1; i >= 0; i--) {
            final CurrentNews model = newsList.get(i);
            if (!items.contains(model)) {
                newsList.remove(model);
            }
        }
        newsList.addAll((Collection<CurrentNews>) items);
        newsList.endBatchedUpdates();
    }

    public void setComparator(Comparator<CurrentNews> comparator) {
        this.mComparator = comparator;
    }

    /**
     * Class for displaying individual items in the list.
     */
    public class NewsViewHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final TextView body;
        private final TextView date;
        private final TextView imageText;
        private final NetworkImageView image;

        public NewsViewHolder(View textView) {
            super(textView);
            title = textView.findViewById(R.id.title);
            body = textView.findViewById(R.id.body_text);
            date = textView.findViewById(R.id.publicerad);
            imageText = textView.findViewById(R.id.image_text);
            image =  textView.findViewById(R.id.image);
        }

        public void bind(final CurrentNews item ,final OnItemClickListener listener) {
            title.setText(item.getTitel());
            body.setText(parseString(item.getSummary()));
            date.setText(item.getPublicerad());
            imageText.setText(item.getImg_fotograf());


            if(item.getImg_url() != null){
                image.setVisibility(View.VISIBLE);
                //Fix better default image... maybe
                image.setDefaultImageResId(R.drawable.ic_placeholder_image_web);
                image.setImageUrl("http://riksdagen.se" + item.getImg_url(),
                        RikdagskollenApp.getInstance().getRequestManager().getmImageLoader());
            }
            else {
                image.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);

                }
            });
        }

        //Maybe do this better......
        private String parseString(String s){
            s = Html.fromHtml(s).toString();
            s = s.replaceAll("&nbsp;"," ");
            s = s.substring(3,s.length()-4); //remove <p> and </p>
            return s;
        }
    }



}
