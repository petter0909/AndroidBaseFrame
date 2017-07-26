package com.third.party.library.views.city;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.third.party.library.R;

import java.util.List;

public class CityAdapter extends ContactListAdapter
{

	public CityAdapter(Context _context, int _resource,
					   List<ContactItemInterface> _items)
	{
		super(_context, _resource, _items);
		
	}
	
	

	public void populateDataForRow(View parentView, ContactItemInterface item,
								   int position)
	{
		View infoView = parentView.findViewById(R.id.infoRowContainer);
		TextView nicknameView = (TextView) infoView
				.findViewById(R.id.cityName);

		String info = item.getDisplayInfo();
		if (info.contains("#")){
			nicknameView.setText(info.replace("#",""));
		}else {
			nicknameView.setText(info);

		}
	}

}
