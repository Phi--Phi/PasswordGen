/* Copyright 2018 Philip Klein

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package gui.element;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.PasswordGeneratorZoneGUI;

/**
 * 
 * This class is responsible for the visualization of the Zone location. 
 * It adds click-and-drag functionality to setting the Zone. This provides
 * an interface for the start and end positions of the Zone.
 */
public class Zone extends JPanel {

	private static final long serialVersionUID = -0x8C3F34BDF7C8B93L;
	
	private volatile JRectangle left, right;
	private JPanel contentPane;
	volatile int totalSegments, startSegment, endSegment;
	PasswordGeneratorZoneGUI parent;
	int width, height;

	public Zone(int segments, int start, int end, PasswordGeneratorZoneGUI parent) {
		totalSegments = segments;
		startSegment = start;
		endSegment = end;
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setVisible(true);
		setLayout(new GridBagLayout());
		contentPane.setLayout(new BorderLayout());
		width = 0; height = 0;
		this.parent = parent;
		setOpaque(false);
		left = new JRectangle(JRectangle.LEFT, this);
		right = new JRectangle(JRectangle.RIGHT, this);
		addLeftRight();
	}

	public synchronized void setSize(int size) {
		totalSegments = size;
		startSegment = 0;
		endSegment = totalSegments;
		
		addLeftRight();
	}
	
	/**This function is for recalculating the layout for the two JRectangles.
	 * Whenever the zone changes the start or end positions, this function is
	 * called to move the corresponding JRectangles.
	 * 
	 */
	public void addLeftRight() {
		int outside, leftInset, rightInset;
		JLabel label = new JLabel();
		GridBagConstraints gbc = new GridBagConstraints();
		leftInset = width/totalSegments;
		leftInset*=startSegment;
		rightInset = width/totalSegments;
		rightInset*=totalSegments-endSegment;
		gbc.fill=GridBagConstraints.BOTH;
		gbc.weighty = gbc.weightx = 0.3;
		gbc.gridheight = 1;
		gbc.gridy = 0;
		gbc.gridwidth = getLength();
		gbc.gridx = startSegment;
		gbc.insets = new Insets(0,leftInset,0,rightInset);
		removeAll();
		add(contentPane, gbc);
		outside = contentPane.getWidth()/getLength();
		contentPane.removeAll();
		label.setPreferredSize(new Dimension(contentPane.getWidth() - outside, height));
		contentPane.add(left, BorderLayout.WEST);
		contentPane.add(right, BorderLayout.EAST);
		contentPane.add(label, BorderLayout.CENTER);
		
	}
	
	/**
	 * This function moves the start and unlocks/locks the corresponding
	 * CharacterBoxes from the PasswordGeneratorZoneGUI.
	 * @param newstart the new position to move the start to.
	 */
	public synchronized void moveStart(int newstart) {
		int diff = startSegment-newstart;
		if(diff > 0)
			for (int i = startSegment - 1; i >= newstart; --i)
				parent.claim(i);
		else if(diff < 0)
			for (int i = startSegment; i < newstart; ++i)
				parent.free(i);
		startSegment = newstart;
		addLeftRight();
		repaint();
	}
	/**
	 * This function moves the end and unlocks/locks the corresponding
	 * CharacterBoxes from the PasswordGeneratorZoneGUI.
	 * @param newend the new position to move the end to.
	 */
	public synchronized void moveEnd(int newend) {
		int diff = endSegment-newend;
		if(diff > 0)
			for (int i = endSegment - 1; i >= newend; --i)
				parent.free(i);
		else if(diff < 0)
			for (int i = endSegment; i < newend; ++i)
				parent.claim(i);
		endSegment = newend;
		addLeftRight();
		repaint();
	}
	
	/**
	 * 
	 * @return the number of characters enclosed by this zone.
	 */
	public synchronized int getLength() {
		return endSegment - startSegment;
	}
	
	/**
	 * Overrides the paint component function. This will draw the lines for the Zone
	 * onto itself. The positioning is determined by the startSegment and endSegment
	 * values.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int x1 = Math.round(startSegment * (float) getWidth() / totalSegments + getX()) + 1,
				x2 = Math.round(endSegment * (float) getWidth() / totalSegments + getX()) - 1,
				y = Math.round(0.5f * getHeight() + getY()), y2 = Math.round(0.85f * getHeight() + getY()),
				y1 = y + y - y2;
		g.setColor(Color.BLACK);
		g.drawLine(x1, y, x2, y);
		g.drawLine(x1, y1, x1, y2);
		g.drawLine(x2, y1, x2, y2);
		
		width = getWidth();
		height = getHeight();
	}
	
	/**
	 * This function "commits" the movement and repositioning of components in the layout.
	 * When this function is called, the components (in the layout) are visually moved.
	 * This allows for a logical movement before officially moving visually. This only
	 * applies to components set in the layout manager.
	 */
	void VALIDATE() {
		addLeftRight();
		validate();
		repaint();
		contentPane.validate();
		contentPane.repaint();
		left.repaint();
		right.repaint();
		
	}

}
