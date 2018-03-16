package com.projectwork.selfmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.projectwork.selfmanage.R;
import com.projectwork.selfmanage.Task;
import com.projectwork.selfmanage.TaskListSerializable;

public class TaskListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    TaskListSerializable objects;

    public TaskListAdapter(Context context, TaskListSerializable taskList) {
        ctx = context;
        objects = taskList;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.main_content_item, parent, false);
        }

        Task p = getTask(position);

        // заполняем View в пункте списка данными из задания: название, продолжительность, дни повторения, отметка о выполнении
        ((TextView) view.findViewById(R.id.tvDescr)).setText(p.getName());
        ((TextView) view.findViewById(R.id.tvRepeat)).setText(p.getDuration());
        ((TextView) view.findViewById(R.id.tvRepeat)).setText(p.getRepeat());
        //((ImageView) view.findViewById(R.id.ivImage)).setImageResource(p.isChecked());

        /*CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
        // присваиваем чекбоксу обработчик
        cbBuy.setOnCheckedChangeListener(myCheckChangeList);
        // пишем позицию
        cbBuy.setTag(position);
        // заполняем данными из товаров: в корзине или нет
        cbBuy.setChecked(p.box);*/
        return view;
    }

    Task getTask(int position) {
        return ((Task) getItem(position));
    }

    /*// содержимое корзины
    ArrayList<Task> getBox() {
        ArrayList<Task> box = new ArrayList<Task>();
        for (Task p : objects) {
            // если в корзине
            if (p.box)
                box.add(p);
        }
        return box;
    }

    // обработчик для чекбоксов
    OnCheckedChangeListener myCheckChangeList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // меняем данные товара (в корзине или нет)
            getTask((Integer) buttonView.getTag()).box = isChecked;
        }
    };*/
}