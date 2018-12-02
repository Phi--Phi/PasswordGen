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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.PasswordGeneratorZoneGUI;

public class Zone extends JPanel {

	private static final long serialVersionUID = -0x8C3F34BDF7C8B93L;

	public class JRectangle extends JPanel implements MouseMotionListener, MouseListener {

		private static final long serialVersionUID = 0x6F01D0429888F5FFL;
		private volatile boolean dragging;
		public static final int LEFT = -1, RIGHT = 1;
		private int direction;
		private int oldStart, oldEnd;
		private Zone z;

		public JRectangle(Cursor c, int direction, Zone p) {
			setCursor(c);
			setOpaque(false);
			this.direction = direction;
			addMouseMotionListener(this);
			addMouseListener(this);
			z=p;
		}
		
		public int getWidth() {
			return z.getWidth();
		}
		
		public int getX() {
			return z.getX();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (!dragging)
				return;
			double mx = e.getX(), length = width / totalSegments, x1 = length * startSegment,
					x2 = length * (endSegment - 1);
			int index, lb, ub;
			if ((mx < x1 || mx > x1) && direction == LEFT) {
				ub = endSegment - 1;
				lb = parent.scan(startSegment, LEFT);
				index = oldStart + (int) Math.round(mx / length);
				if (index <= ub && index >= lb)
					moveStart(index);
			} else if ((mx > x2 || mx < x2) && direction == RIGHT) {
				ub = parent.scan(endSegment, RIGHT);
				lb = startSegment + 1;
				index = oldEnd + (int) Math.round(mx / length);
				if (index <= ub && index >= lb)
					moveEnd(index);
			}
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			double mx = e.getX(), length = width / totalSegments;
			if ((mx < length * startSegment || mx > length * startSegment) && direction == LEFT) {
				oldStart = startSegment;
				dragging = true;
			} else if ((mx > length * endSegment || mx < length * endSegment) && direction == RIGHT) {
				oldEnd = endSegment;
				dragging = true;
			}

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			dragging = false;
			VALIDATE();
		}

		public int getDirection() {
			return direction;
		}
		
	}

	private volatile int totalSegments, startSegment, endSegment;
	private PasswordGeneratorZoneGUI parent;
	private volatile JRectangle left, right;
	private JPanel contentPane;
	private int width, height;

	public Zone(int segments, PasswordGeneratorZoneGUI parent) {
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setVisible(true);
		setLayout(new GridBagLayout());
		contentPane.setLayout(new BorderLayout());
		height = width = 0;
		this.parent = parent;
		setOpaque(false);
		setSize(segments);
		
	}

	public Zone(int segments, int start, PasswordGeneratorZoneGUI parent) {
		this(segments, parent);
		startSegment = start;
	}

	public Zone(int segments, int start, int end, PasswordGeneratorZoneGUI parent) {
		this(segments, start, parent);
		endSegment = end;
	}

	public synchronized void setSize(int size) {
		
		left = new JRectangle(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR), JRectangle.LEFT, this);
		right = new JRectangle(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR), JRectangle.RIGHT, this);
		totalSegments = size;
		startSegment = 0;
		endSegment = totalSegments;
		
		addLeftRight();
	}
	
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

	public synchronized int getLength() {
		return endSegment - startSegment;
	}
	
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
	
	private void VALIDATE() {
		addLeftRight();
		validate();
		repaint();
		contentPane.validate();
		contentPane.repaint();
		left.repaint();
		right.repaint();
		
	}

}
