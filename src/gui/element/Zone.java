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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

import gui.PasswordGeneratorZoneGUI;

public class Zone extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -631615879959579539L;

	public class JRectangle extends JPanel implements MouseMotionListener, MouseListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7998903397632243199L;
		private volatile boolean dragging = false;
		public static final int LEFT = -1, RIGHT = 1;
		private int direction;

		public JRectangle(Cursor c, int direction) {
			setCursor(c);
			setOpaque(false);
			this.direction = direction;
			addMouseMotionListener(this);
			addMouseListener(this);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (dragging) {
				double mx = e.getX();
				double length = getWidth() / totalSegments;
				double x1 = (length * startSegment) + getX();
				double x2 = (length * endSegment) + getX();
				int index, lb, ub;

				if ((mx < x1 || mx > x1) && direction == LEFT) {
					ub = endSegment - 1;
					lb = parent.scan(startSegment, LEFT);
					index = (int) Math.round(mx / length);
					if(index <= ub && index >= lb)
						moveStart(index);
				} else if ((mx > x2 || mx < x2) && direction == RIGHT) {
					ub = parent.scan(endSegment, RIGHT);
					lb = startSegment + 1;
					index = (int) Math.round(mx / length);
					if(index <= ub && index >= lb)
						moveEnd(index);
				}
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
			double mx = e.getX();
			double x1 = ((getWidth() / totalSegments) * startSegment) + getX();
			double x2 = ((getWidth() / totalSegments) * endSegment) + getX();

			if ((mx < x1 || mx > x1) && direction == LEFT) {
				dragging = true;
			} else if ((mx > x2 || mx < x2) && direction == RIGHT) {
				dragging = true;
			}

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			dragging = false;
		}

		public int getDirection() {
			return direction;
		}

	}

	private volatile int totalSegments, startSegment, endSegment;
	private PasswordGeneratorZoneGUI parent;
	private volatile JRectangle left, right;

	public Zone(int segments, PasswordGeneratorZoneGUI parent) {
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
		left = new JRectangle(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR), JRectangle.LEFT);
		right = new JRectangle(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR), JRectangle.RIGHT);
		totalSegments = size;
		startSegment = 0;
		endSegment = totalSegments;
	}

	public synchronized void moveStart(int newstart) {
		startSegment = newstart;
		repaint();
	}

	public synchronized void moveEnd(int newend) {
		endSegment = newend;
		repaint();
	}

	public void paint(Graphics g) {
		int x1 = Math.round((((float) getWidth() / totalSegments) * startSegment) + getX());
		int x2 = Math.round((((float) getWidth() / totalSegments) * endSegment) + getX());
		int y = Math.round((getHeight() * 0.5f) + getY());
		int y2 = Math.round((getHeight() * 0.85f) +getY());
		g.setColor(Color.BLACK);
		g.drawLine(x1, y, x2, y);
		g.drawLine(x1, y2, x1, y2);
		g.drawLine(x2, y2, x2, y2);
		
	}

}
