
newImage("out", "8-bit black", 370, 283, 1);
run("Add Slice");
wait( 1000 );

z = 1;

for ( t = 1; t <=426; t = t+1)
{
	if ( t == 1 )
	{
		for ( i = 1; i <= 36+14; i = i + 1 )
		{
			z = i;
			duplicate(mod(i),t);
		}
	}
	else if ( t == 2 )
	{
		for ( i = 2; i <= 15; i = i + 1 )
		{
			duplicate(mod(z),i);
		}
		for ( i = 14; i >= 2; i = i - 1 )
		{
			duplicate(mod(z),i);
		}
	}
	else
	{
			duplicate(mod(z),t);
			if (t%4==0)
			{
			z = z + 1;
			}
	}
}

function mod(i)
{
	return ((i-1)%36+1);
}

function duplicate(z, t) {
	selectWindow("input");
	setSlice(z + (t-1)*36);
	run("Copy");
	selectWindow("out");
	run("Add Slice");
	run("Paste");
	drawString("t="+t, 320, 270);
}