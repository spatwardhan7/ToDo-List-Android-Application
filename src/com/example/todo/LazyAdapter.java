package com.example.todo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private String[] name,priority,duedate,duetime,completedFlag;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter(Activity a, String[] lname, String[] lduedate,String[] lduetime,String[] lpriority, String[] lcompletedFlag) {
        activity = a;
        name=lname;
        priority=lpriority;
        duedate=lduedate;
        duetime=lduetime;
        completedFlag=lcompletedFlag;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return name.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.item, null);

        TextView text1=(TextView)vi.findViewById(R.id.name);
        TextView text2=(TextView)vi.findViewById(R.id.duedate);
        TextView text3=(TextView)vi.findViewById(R.id.duetime);
        TextView text4=(TextView)vi.findViewById(R.id.priority);
        ImageView image=(ImageView)vi.findViewById(R.id.image);
        text1.setText(name[position]);
        text2.setText(priority[position]);
        
        final int unchecked_id=R.drawable.stub;
        final int checked_id=R.drawable.icon;
        //imageLoader.DisplayImage(image, completedFlag[position]);
        String flag=completedFlag[position];
        System.out.println("Showed image");
        if(flag.equals("1"))
        	{image.setImageResource(checked_id);
        text1.setPaintFlags(text1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        text2.setPaintFlags(text2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        text3.setPaintFlags(text3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        text4.setPaintFlags(text4.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        	}
        
        if(flag.equals("0"))
        {	image.setImageResource(unchecked_id);
        text1.setPaintFlags( text1.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        text2.setPaintFlags( text2.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        text3.setPaintFlags( text3.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        text4.setPaintFlags( text4.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }

        
        
        if(duedate[position].equals("-1"))
        {
        	text3.setText("---");
        }
        else
        {
        	text3.setText(duedate[position]);
        }
        if(duetime[position].equals("-1"))
        {
        	text4.setText("--");
        }
        else
        {
        	text4.setText(duetime[position]);
        }
        

        return vi;
    }
}