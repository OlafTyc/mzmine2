/*
 * Copyright 2006-2011 The MZmine 2 Development Team
 * 
 * This file is part of MZmine 2.
 * 
 * MZmine 2 is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * MZmine 2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * MZmine 2; if not, write to the Free Software Foundation, Inc., 51 Franklin St,
 * Fifth Floor, Boston, MA 02110-1301 USA
 */

package net.sf.mzmine.modules.visualization.tic;

import net.sf.mzmine.data.ChromatographicPeak;
import net.sf.mzmine.data.DataPoint;
import net.sf.mzmine.data.RawDataFile;
import net.sf.mzmine.data.Scan;

import org.jfree.data.xy.AbstractXYDataset;

/**
 * Integrated peak area data set. Separate data set is created for every peak
 * shown in this visualizer window.
 */
public class PeakDataSet extends AbstractXYDataset {

	private ChromatographicPeak peak;
	private double retentionTimes[], intensities[], mzValues[];

	public PeakDataSet(ChromatographicPeak peak) {

		this.peak = peak;

		int scanNumbers[] = peak.getScanNumbers();
		RawDataFile dataFile = peak.getDataFile();

		retentionTimes = new double[scanNumbers.length];
		intensities = new double[scanNumbers.length];
		mzValues = new double[scanNumbers.length];

		for (int i = 0; i < scanNumbers.length; i++) {
			Scan scan = dataFile.getScan(scanNumbers[i]);
			DataPoint dataPoint = peak.getDataPoint(scanNumbers[i]);

			retentionTimes[i] = scan.getRetentionTime();
			if (dataPoint == null) {
				mzValues[i] = 0;
				intensities[i] = 0;
			} else {
				mzValues[i] = dataPoint.getMZ();
				intensities[i] = dataPoint.getIntensity();
			}

		}
	}

	@Override
	public int getSeriesCount() {
		return 1;
	}

	@Override
	public Comparable getSeriesKey(int series) {
		return peak.toString();
	}

	public int getItemCount(int series) {
		return retentionTimes.length;
	}

	public Number getX(int series, int item) {
		return retentionTimes[item];
	}

	public Number getY(int series, int item) {
		return intensities[item];
	}

	double getMZ(int series, int item) {
    	return mzValues[item]; 
    }
}
