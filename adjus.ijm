
for ( i = 15; i <=143; i = i+1)
{
value = 0.15 + i*0.003797203;
scale = 0.693/value;
print( "slice " + i + ": " + scale );

	for ( s = 1; s <= 283; s = s + 1)
	{
		setSlice(s + (i-1)*283);
		run("Multiply...", "value=" + scale + " slice");
		//print( "set " + (s + (i-1)*283) );
	}
}