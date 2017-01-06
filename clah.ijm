for ( t = 1; t <=503; t = t+1)
{
setSlice(t);
run("Enhance Local Contrast (CLAHE)", "blocksize=63 histogram=256 maximum=2 mask=*None*");
}