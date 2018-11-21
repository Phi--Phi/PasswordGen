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
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class Zone extends JComponent {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -631615879959579539L;

	public class Left implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	

	public class Right implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class JRectangle extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2429536843663657761L;
		public JRectangle(Cursor c) {
			setCursor(c);
			setOpaque(false);
		}
		
	}
	
	private volatile int totalSegments, startSegment,endSegment;
	private volatile JRectangle left, right;
	
	public Zone(int segments) {
		left = new JRectangle(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
		right = new JRectangle(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
		totalSegments = segments;
		startSegment = 0;
		endSegment = totalSegments;
	}
	
	public Zone (int segments, int start) {
		this(segments);
		startSegment = start;
	}
	
	public Zone (int segments, int start, int end) {
		this(segments, start);
		endSegment = end;
	}
	
	public synchronized void moveStart (int newstart) {
		startSegment = newstart;
	}
	
	public synchronized void moveEnd(int newend) {
		endSegment = newend;
	}
	
	public void paint(Graphics g) {
		
	}
	
	

}
