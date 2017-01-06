run("TIFF Virtual Stack...", "open=/Users/kkolyva/Documents/experiment/Decon-correct.tif");
for(t = 1; t < 200; t++){
	run("Duplicate...", "duplicate frames=" + t + "-" + t);
	saveAs("Tiff", "/Users/kkolyva/Documents/experiment/folder/new-stack" + t +".tif");
	close();
	selectWindow("Decon-correct.tif");
}
