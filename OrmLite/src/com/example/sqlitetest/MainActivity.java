package com.example.sqlitetest;

import java.sql.SQLException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.j256.ormlite.dao.Dao;

public class MainActivity extends Activity 
{

	private ListView myListView;
	private EditText myEditText;
	private DatabaseHelper main;
	private Dao<SimpleData, Integer> dao;
	private static int id;
	protected final static int MENU_ADD = Menu.FIRST;
	protected final static int MENU_EDIT = Menu.FIRST + 1;
	protected final static int MENU_DELETE = Menu.FIRST + 2;

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, MENU_ADD, 0, R.string.ADD);
		menu.add(Menu.NONE, MENU_EDIT, 0, R.string.EDIT);
		menu.add(Menu.NONE, MENU_DELETE, 0, R.string.DELETE);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		
		super.onOptionsItemSelected(item);
		switch (item.getItemId())
		{
		case MENU_ADD:
			try
			{
				operation("add");
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			break;
		case MENU_EDIT:
			try
			{
				operation("edit");
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			break;
		case MENU_DELETE:
			try
			{
				operation("delete");
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		try
		{
			fillList();
		} catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return true;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		main = new DatabaseHelper(getApplication());
		try
		{
			dao = main.getSimpleDataDao();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myEditText = (EditText) findViewById(R.id.EditText1);
		myListView = (ListView) findViewById(R.id.ListView1);

		try
		{
			fillList();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void fillList() throws SQLException
	{

		List<SimpleData> list = dao.queryForAll();
		ArrayAdapter<SimpleData> arrayAdapter = new CountsAdapter(this,
				android.R.layout.simple_expandable_list_item_1, list);
		myListView.setAdapter(arrayAdapter);
		myListView.setOnItemClickListener(new OnItemClickListener()
		{

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				// TODO Auto-generated method stub
				SimpleData simpleData = (SimpleData) myListView.getAdapter()
						.getItem(arg2);
				id = simpleData.getId();
				myEditText.setText(simpleData.getString());
			}
		});
	}

	private void operation(String cmd) throws SQLException
	{

		SimpleData simpleData = new SimpleData();
		if (myEditText.getText().toString().equals(""))
			return;
		if (cmd == "add")
		{
			simpleData.setString(myEditText.getText().toString());
			dao.create(simpleData);
		}
		if (cmd == "edit")
		{
			SimpleData sim = dao.queryForId(id);
			sim.changeValue(myEditText.getText().toString());
			dao.update(sim);
		}
		if (cmd == "delete")
		{
			dao.deleteById(id);
		}
		// myCursor.requery();

	}

	private class CountsAdapter extends ArrayAdapter<SimpleData>
	{

		public CountsAdapter(Context context, int textViewResourceId,
				List<SimpleData> items)
		{
			super(context, textViewResourceId, items);
		}
	}
}
