package nucleimapping;

import java.io.File;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CountNuclei {

	long timeStep = 30; // seconds, time step between acquisitions
	long [] spotsNumberPerFrame; // stores number of nuclei per frame (by definition this array sorted in non-descending order)
	
	private final static boolean debug = true;

	// create the dictionary number of nuclei -> time frame
	public void createLookUpTable(File file){
		try{		
			DocumentBuilderFactory dbFactrory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactrory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();

			if (debug)  System.out.println("Root element should be \"TrackMate\": " + doc.getDocumentElement().getNodeName());
			// grab all time frames
			NodeList tFrameList = doc.getElementsByTagName("SpotsInFrame"); 	
			
			// not valid because some entries in the end of the list might be empty
			// int numTimePoints = tFrameList.getLength();	
			int numTimePoints = 0;
			for (int j = 0; j < tFrameList.getLength(); j++){
				Element tFrame = (Element) tFrameList.item(j);
				if (tFrame.getNodeName().matches("SpotsInFrame")){						
					NodeList spots = tFrame.getElementsByTagName("Spot");	
					if (spots.getLength() > 0) // != 0 ?
						numTimePoints++;
				}
			}
			
			// TODO: FIND OUT WHY THE NUMBER OF SPOTS IS WRONG!
			// DEBUG: REMOVE WHEN FIXED
			// SOLUTION: SOME IMAGES WERE NOT SEGMENTATED 
			// numTimePoints = 110;
			
			System.out.println(numTimePoints);
			
			spotsNumberPerFrame = new long[numTimePoints];
			if (debug) System.out.println("# time frames: " + tFrameList.getLength());
			
			
			long [] numMissingNuclei = new long[numTimePoints];  
			fixMissingNuclei(numMissingNuclei);
			
			
			for (int j = 0; j < numTimePoints; j++){
				Element tFrame = (Element) tFrameList.item(j);
				if (tFrame.getNodeName().matches("SpotsInFrame")){						
					NodeList spots = tFrame.getElementsByTagName("Spot");
					if (debug) System.out.println(j + " " + spots.getLength());
					// TODO: add the correction to the total number (# of points that disappear)
					spotsNumberPerFrame[j] = spots.getLength();
					spotsNumberPerFrame[j] += numMissingNuclei[j]; // add missing nuclei
				}
				else
					if (debug) System.out.println("IT'S A TRAP!");
			}			
		}

		catch(Exception e){
			System.out.println("Something went wrong. Use debug varaible to find a bug.");
		}
	}
	
	// this fix is empirical 
	// some nuclei might still be missing 
	public void fixMissingNuclei(long[] numMissingNuclei){
		for (int j = 0; j < numMissingNuclei.length; j++){
			if (j >= 181)
				numMissingNuclei[j] += 1;
			if (j >= 170)
				numMissingNuclei[j] += 1;
			if (j >= 172)
				numMissingNuclei[j] += 1;
			if (j >= 177)
				numMissingNuclei[j] += 1;
			if (j >= 178)
				numMissingNuclei[j] += 1;
			if (j >= 181)
				numMissingNuclei[j] += 1;
			if (j >= 160)
				numMissingNuclei[j] += 1;
			if (j >= 174)
				numMissingNuclei[j] += 1;
			if (j >= 178)
				numMissingNuclei[j] += 1;
			if (j >= 176)
				numMissingNuclei[j] += 1;
		
			if (j >= 103) // germ  ? 
				numMissingNuclei[j] += 1;
		}
	}
	
	// TODO: adjust to the cases when the number of the nuclei is not in the list
	// returns the time Frame for the given index in the spotsNumberPerFrame
	public long idxToTimeFrame(long idx, long timeStep){
		return timeStep*idx;
	}
	
	public long getIdx(long numSpots, long[] spotsNumberPerFrame){
		long idx = Arrays.binarySearch(spotsNumberPerFrame, numSpots);
		// idx can be negative, check the binarySearch(...) description
		// TODO: add a smarter way to fix this problem
		// there should be a loop to check that it is really the last frame with the given number of spots 
		if (idx < 0){
			idx = -(idx + 1); 
		}
		
		return idx;
	} 
	
	public void runSearch(File file, long numQuerySpots){
		createLookUpTable(file);	
		long index = getIdx(numQuerySpots, spotsNumberPerFrame);
		long timePoint = idxToTimeFrame(index, timeStep);
		System.out.println("Your idx should be " + index);
		System.out.println("Your timepoint should be " + timePoint);
	}
	
	public static void main(String[] args){
		// file with the annotation
		File file = new File("/Users/kkolyva/Documents/experiment/Raw-mamut.xml");
		// number of the cells in the static image
		// TODO: write a smart algorithm to count the cells
		long numQuerySpots = 169; 
		new CountNuclei().runSearch(file, numQuerySpots);
		System.out.println("Doge!");
	}


}
