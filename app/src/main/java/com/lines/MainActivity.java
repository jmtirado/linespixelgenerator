package com.lines;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends Activity {
	
	TextView aux;
	int onclick, maxpuntos=0, lastclick, firstclick;
	Point p1, p2;
	TableLayout tl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tl = (TableLayout)findViewById(R.id.tablelayout);
		Button bgenlin = (Button)findViewById(R.id.genline);
		Button bclean = (Button)findViewById(R.id.clean);
	
		//ADD TAG TO KNOW WHAT IS ACTIVE
		for(int i=0; i<10;i++)
		{
			TableRow row = (TableRow)tl.getChildAt(i);
			for(int j=0;j<10;j++)
			{
				TextView vew = (TextView)row.getChildAt(j);
				vew.setTag(Integer.valueOf(0));
			}
		}
		
		//BUTTON GENERATE LINE
		bgenlin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(maxpuntos==2)
				{
					putline(p1, p2);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "REQUIRES TWO POINTS",	Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		//BUTTON CLEAN
		bclean.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				for(int i=0; i<10;i++)
				{
					TableRow row = (TableRow)tl.getChildAt(i);
					for(int j=0;j<10;j++)
					{
						TextView vew = (TextView)row.getChildAt(j);
						vew.setTag(Integer.valueOf(0));
						vew.setBackgroundResource(R.drawable.cellstyle);
						maxpuntos = 0;
					}
				}				
			}
		});
		
		
	}
	
	//IF CLICK IN ONE SQUARE LAUNCH THIS EVENT
	public void onClick(View v)
	{
		Resources res = v.getResources();
		aux = (TextView)findViewById(v.getId());
		String auxs = res.getResourceEntryName(v.getId());
		
		//IF HAVE 2 SQUARES PRESSED
		if(maxpuntos==2)
		{
			
			if(lastclick == v.getId())
			{
				
				--maxpuntos;
				aux.setTag(Integer.valueOf(0));
				aux.setBackgroundResource(R.drawable.cellstyle);
				
			}
			else if(firstclick == v.getId())
			{
				--maxpuntos;
				aux.setTag(Integer.valueOf(0));
				aux.setBackgroundResource(R.drawable.cellstyle);
				p1 = p2;
				firstclick = lastclick;
				
			}
			else
			{
				
			}
		
			
		}
		else
		{
			
			onclick = (Integer)aux.getTag();
			if(onclick == 1)
			{
				--maxpuntos;
				aux.setTag(Integer.valueOf(0));
				aux.setBackgroundResource(R.drawable.cellstyle);
				
			}
			else
			{
				++maxpuntos;
				if(maxpuntos == 1)
				{
					firstclick = v.getId();
					p1 = new Point(Character.getNumericValue( auxs.charAt(1)),Character.getNumericValue( auxs.charAt(2)));
					
				}
				else
				{
					lastclick = v.getId();
					p2 = new Point(Character.getNumericValue( auxs.charAt(1)),Character.getNumericValue( auxs.charAt(2)));
					
					
				
				}
				aux.setTag(Integer.valueOf(1));
				aux.setBackgroundResource(R.drawable.cellstyle_onclick);
			}
		}
				
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//PUT A PIXEL IN ONE POINT
	public void putpixel(Point p)
	{
		for(int i=0; i<=p.getX();i++)
		{
			TableRow row = (TableRow)tl.getChildAt(i);
			for(int j=0;j<=p.getY();j++)
			{
				if(i== p.getX() && j==p.getY())
				{
					TextView vew = (TextView)row.getChildAt(j);
					vew.setTag(Integer.valueOf(1));
					vew.setBackgroundResource(R.drawable.cellstyle_onclick);
					Log.i("PUTPIXEL ", "X: "+p.getX() + "Y: "+ p.getY());
				}
			}
		}
	}
	
	//GENERATOR OF LINES
	public void putline(Point pt1, Point pt2)
	{
		 int fx, fy, dx, dy, p, incE, incNE, stepx, stepy;
		
		dx = (pt2.getX() - pt1.getX());
		dy = (pt2.getY() - pt1.getY());
		//CHECK RECEIVED POINTS
		Log.i("RECEIVED POINTS ", "P1: " + "X:"+ pt1.getX()+" Y:"+pt1.getY());
		Log.i("RECEIVED POINTS ", "P2: " + "X:"+ pt2.getX()+" Y:"+pt2.getY());
		
		//DETERMINE WHAT POINT TO START
		if (dy < 0)
		{
			dy = -dy;
			stepy = -1;
		}
		else
		{
			stepy = 1;
		}
		
		if (dx < 0)
		{
			dx = -dx;
			stepx = -1;
		}
		else
		{
			stepx = 1;
		}
		
		fx = pt1.getX();
		fy = pt1.getY();
		
		//"WHILE" UNTIL END OF LINE
		if(dx>dy)
		{
			p = 2*dy - dx;
			incE = 2*dy;
			incNE = 2*(dy-dx);
			
			while(fx != pt2.getX())
			{
				fx = fx + stepx;
				if(p < 0)
				{
					p = p + incE;
				}
				else
				{
					fy = fy + stepy;
					p = p+ incNE;
				}
				Point f = new Point(fx,fy);
				Log.i("FINAL POINT dx>dy ", "X:"+fx + " Y:"+ fy);
				putpixel(f);
			}
		}
		else
		{
			p = 2*dx - dy;
			incE = 2*dx;
			incNE = 2*(dx-dy);
			
			while(fy != pt2.getY())
			{
				fy = fy + stepy;
				if(p < 0)
				{
					p = p + incE;
				}
				else
				{
					fx = fx + stepx;
					p = p + incNE;
				}
				Point f = new Point(fx,fy);
				Log.i("FINAL POINT else ", "X:"+fx + " Y:"+ fy);
				putpixel(f);
			}
		}
		
	}

}
