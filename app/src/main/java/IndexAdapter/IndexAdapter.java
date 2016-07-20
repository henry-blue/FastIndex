package IndexAdapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.fastindex.R;

import java.util.ArrayList;
import java.util.List;

import bean.Person;


public class IndexAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Person> mPersons;

    public IndexAdapter(Context context, ArrayList<Person> mArrayLists) {
        mContext = context;
        mPersons = mArrayLists;
    }

    @Override
    public int getCount() {
        return mPersons.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = View.inflate(mContext, R.layout.list_item, null);
        } else {
            view = convertView;
        }
        ViewHolder viewHolder = ViewHolder.getHolder(view);
        Person person = mPersons.get(position);
        String currletter = person.getPinyin().charAt(0) + "";
        String str = null;
        if (position == 0) {
            str = currletter;
        } else {
            String perLetter = mPersons.get(position - 1).getPinyin().charAt(0) + "";
            if (!TextUtils.equals(perLetter, currletter)) {
                str = currletter;
            }
        }
        viewHolder.mIndex.setVisibility(str == null ? View.GONE : View.VISIBLE);
        viewHolder.mIndex.setText(currletter);
        viewHolder.mName.setText(person.getName());
        return view;
    }

    static class ViewHolder {
        TextView mIndex;
        TextView mName;
        public static ViewHolder getHolder(View view) {
           Object tag = view.getTag();
            if (tag != null) {
                return (ViewHolder) tag;
            } else {
                ViewHolder holder = new ViewHolder();
                holder.mIndex = (TextView) view.findViewById(R.id.tv_index);
                holder.mName = (TextView) view.findViewById(R.id.tv_name);
                view.setTag(holder);
                return holder;
            }
        }
    }
}
