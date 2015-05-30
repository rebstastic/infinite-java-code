package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Layout suitable for graphic representation of a calculator. A grid-like
 * layout consists of 5 rows and 7 columns. Position for first component - (1,
 * 1) - has the largest width and it extends through positions (1, 1), (1, 2),
 * (1, 3), (1, 4) and (1, 5). Every other component occupies one position.
 * Spacing between positions can be provided. If not, spacing is zero.
 * </p>
 * 
 * <table summary="CalcLayout" border="1" width="350" style="text-align:center;">
 * <tr align="center">
 * <td colspan="5">(1, 1)</td>
 * <td >(1, 6)</td>
 * <td >(1, 7)</td>
 * </tr>
 * <tr align="center">
 * <td >(2, 1)</td>
 * <td >(2, 2)</td>
 * <td >(2, 3)</td>
 * <td >(2, 4)</td>
 * <td >(2, 5)</td>
 * <td >(2, 6)</td>
 * <td >(2, 7)</td>
 * </tr>
 * <tr align="center">
 * <td>(3, 1)</td>
 * <td>(3, 2)</td>
 * <td>(3, 3)</td>
 * <td>(3, 4)</td>
 * <td>(3, 5)</td>
 * <td>(3, 6)</td>
 * <td>(3, 7)</td>
 * </tr>
 * <tr align="center">
 * <td>(4, 1)</td>
 * <td>(4, 2)</td>
 * <td>(4, 3)</td>
 * <td>(4, 4)</td>
 * <td>(4, 5)</td>
 * <td>(4, 6)</td>
 * <td>(4, 7)</td>
 * </tr>
 * <tr align="center">
 * <td>(5, 1)</td>
 * <td>(5, 2)</td>
 * <td>(2, 3)</td>
 * <td>(5, 4)</td>
 * <td>(5, 5)</td>
 * <td>(5, 6)</td>
 * <td>(5, 7)</td>
 * </tr>
 * </table>
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class CalcLayout implements LayoutManager2 {

	/**
	 * Enum used for determination which of {@code getMinimumSize},
	 * {@code getPreferredSize} or {@code getMaximumSize} methods to use when
	 * searching for component size.
	 * 
	 * @author Petra Rebernjak - 0036472203
	 *
	 */
	private static enum Size {
		/**
		 * Used when {@code getMinimumSize} method needs to be invoked on
		 * component.
		 */
		MINIMUM,
		/**
		 * Used when {@code getPreferredSize} method needs to be invoked on
		 * component.
		 */
		PREFERRED,
		/**
		 * Used when {@code getMaximumSize} method needs to be invoked on
		 * component.
		 */
		MAXIMUM;

		/**
		 * Returns predefined size of the component.
		 * 
		 * @param comp
		 *            Component to calculate size of.
		 * @return Predefined size of the component.
		 */
		Dimension getPredefinedSize(Component comp) {
			Dimension d = null;
			if (this.equals(Size.MINIMUM)) {
				d = comp.getMinimumSize();
			} else if (this.equals(Size.PREFERRED)) {
				d = comp.getPreferredSize();
			} else if (this.equals(Size.MAXIMUM)) {
				d = comp.getMaximumSize();
			}
			return d;
		}
	}

	/**
	 * Number of layout rows.
	 */
	private static final int NUM_ROWS = 5;

	/**
	 * Number of layout columns.
	 */
	private static final int NUM_COLS = 7;

	/**
	 * Number of columns that first component occupies.
	 */
	private static final int NUM_COLS_FIRST = 5;

	/**
	 * Position of first component.
	 */
	private static final RCPosition FIRS_POSITION = new RCPosition(1, 1);

	/**
	 * Constant used for centar alignment of a component.
	 */
	private static final float CENTER_ALIGNMENT = 0.5f;

	/**
	 * Space between components.
	 */
	private int space;

	/**
	 * Map of components.
	 */
	private Map<Component, RCPosition> components;

	/**
	 * Default constructor. Sets {@code space} to zero.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Constructor.
	 * 
	 * @param space
	 *            Space beteween components.
	 */
	public CalcLayout(int space) {
		this.space = space;
		components = new HashMap<>();
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException(
				"Component not supported in this CalcLayout.");
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		components.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getLayoutSize(parent, Size.PREFERRED);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSize(parent, Size.MINIMUM);
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int width = (parent.getWidth() + subWidthInsets(insets) - getSpacingWidth())
				/ NUM_COLS;
		int height = (parent.getHeight() + subHeightInsets(insets) - getSpacingHeight())
				/ NUM_ROWS;
		for (Component component : parent.getComponents()) {
			RCPosition position = components.get(component);
			int x = insets.left + width * (position.getColumn() - 1) + space
					* position.getColumn();
			int y = insets.top + height * (position.getRow() - 1) + space
					* position.getRow();

			int properWidth = width;
			if (position.equals(FIRS_POSITION)) {
				properWidth = width * NUM_COLS_FIRST + space
						* (NUM_COLS_FIRST - 1);
			}

			component.setBounds(x, y, properWidth, height);
		}

	}

	@Override
	public void addLayoutComponent(Component comp, Object constr) {
		RCPosition constraints = null;

		if (constr instanceof String) {
			constraints = RCPosition.parse((String) constr);
		} else if (constr instanceof RCPosition) {
			constraints = (RCPosition) constr;
		}

		int row = constraints.getRow();
		int column = constraints.getColumn();

		// If constraint with at least one negative index throw exception.
		if (row < 1 || row > NUM_ROWS || column < 1 || column > NUM_COLS) {
			throw new IllegalArgumentException(
					"Given positions cannot be zero or negative: "
							+ constraints.toString());
		}

		// If constraint with indexes (1, 2) - (1, 5) throw exception.
		if (row == 1 && column > 1 && column < 6) {
			throw new IllegalArgumentException(
					"Given positions cannot be (1, 2) - (1, 5): "
							+ constraints.toString());
		}

		// If constraint with indexes that already exists throw exception.
		if (components.containsValue(constraints)) {
			throw new IllegalArgumentException("Given position already full: "
					+ constraints.toString());
		}

		// Add to map.
		components.put(comp, constraints);
	}

	@Override
	public Dimension maximumLayoutSize(Container parent) {
		return getLayoutSize(parent, Size.MAXIMUM);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return CENTER_ALIGNMENT;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return CENTER_ALIGNMENT;
	}

	@Override
	public void invalidateLayout(Container target) {
		// Unused.
	}

	/**
	 * Calculates the layout size based on components size.
	 * 
	 * @param parent
	 *            Parent container.
	 * @param size
	 *            Enum for determining what size to calculate - minimum,
	 *            preferred or maximum.
	 * @return Size of the layout.
	 */
	private Dimension getLayoutSize(Container parent, Size size) {
		int width = 0;
		int height = 0;

		int numberOfComponents = parent.getComponentCount();
		for (int i = 0; i < numberOfComponents; i++) {
			Component component = parent.getComponent(i);
			Dimension d = size.getPredefinedSize(component);
			if (components.get(component).equals(FIRS_POSITION)) {
				d.width = (component.getWidth() - (NUM_COLS_FIRST - 1) * space)
						/ NUM_COLS_FIRST;
			}
			if (i > 0) {
				width = Math.max(width, (int) d.getWidth());
				height = Math.max(height, (int) d.getHeight());
			} else {
				width = d.width;
				height = d.height;
			}
		}

		Insets insets = parent.getInsets();
		width = width * NUM_COLS + addWidthInsets(insets) + getSpacingWidth();
		height = height * NUM_ROWS + addHeightInsets(insets)
				+ getSpacingHeight();

		return new Dimension(width, height);
	}

	private int addWidthInsets(Insets insets) {
		return insets.left + insets.right;
	}

	private int subWidthInsets(Insets insets) {
		return -addWidthInsets(insets);
	}

	private int addHeightInsets(Insets insets) {
		return insets.top + insets.bottom;
	}

	private int subHeightInsets(Insets insets) {
		return -addHeightInsets(insets);
	}

	/**
	 * Returns width of all the spacing between columns.
	 * 
	 * @return Width of all the spacing between columns.
	 */
	private int getSpacingWidth() {
		int spacingWidth = (NUM_COLS + 1) * space;
		return (spacingWidth) == 0 ? 0 : spacingWidth;
	}

	/**
	 * Returns height of all the spacing between rows.
	 * 
	 * @return Height of all the spacing between rows.
	 */
	private int getSpacingHeight() {
		int spacingHeight = (NUM_ROWS + 1) * space;
		return (spacingHeight) == 0 ? 0 : spacingHeight;
	}

}
