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

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

/**
 * This class is responsible enabling the click-and-drag for
 * the Zone. Each Zone has two JRectangles (one on each side)
 * that can be dragged to resize the Zone.
 *
 */
public class JRectangle extends JPanel implements MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 0x6F01D0429888F5FFL;
	private volatile boolean dragging;
	public static final int LEFT = -1, RIGHT = 1;
	private int direction;
	private int oldStart, oldEnd;
	private Zone z;

	public JRectangle(int direction, Zone p) {
		setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		setOpaque(false);
		this.direction = direction;
		addMouseMotionListener(this);
		addMouseListener(this);
		z=p;
	}
	/**
	 * This overrides the getWidth function for the component.
	 * The width of the JRectangle is now equal to the width of the Zone.
	 */
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
		double mx = e.getX(), length = z.width / z.totalSegments, x1 = length * z.startSegment,
				x2 = length * (z.endSegment - 1);
		int index, lb, ub;
		if ((mx < x1 || mx > x1) && direction == LEFT) {
			ub = z.endSegment - 1;
			lb = z.parent.scan(z.startSegment, LEFT);
			index = oldStart + (int) Math.round(mx / length);
			if (index <= ub && index >= lb)
				z.moveStart(index);
		} else if ((mx > x2 || mx < x2) && direction == RIGHT) {
			ub = z.parent.scan(z.endSegment, RIGHT);
			lb = z.startSegment + 1;
			index = oldEnd + (int) Math.round(mx / length);
			if (index <= ub && index >= lb)
				z.moveEnd(index);
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
		double mx = e.getX(), length = z.width / z.totalSegments;
		if ((mx < length * z.startSegment || mx > length * z.startSegment) && direction == LEFT) {
			oldStart = z.startSegment;
			dragging = true;
		} else if ((mx > length * z.endSegment || mx < length * z.endSegment) && direction == RIGHT) {
			oldEnd = z.endSegment;
			dragging = true;
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		dragging = false;
		z.VALIDATE();
	}

	public int getDirection() {
		return direction;
	}
	
}