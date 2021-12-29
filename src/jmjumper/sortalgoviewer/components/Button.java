package jmjumper.sortalgoviewer.components;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

public class Button extends Component implements MouseListener {

    private static final int BORDER_SIZE = 2;
    private static final int BORDER_ARC_SIZE = 30;
    private static final int BACKGROUND_ARC_SIZE = BORDER_ARC_SIZE - BORDER_SIZE;

    private static final Color DEFAULT_BACKGROUND = new Color( 180, 92, 23 );
    private static final Color DEFAULT_PRESS_BACKGROUND = new Color( 0, 0, 0, ( int ) ( 0.3 * 255 ) );
    private static final Color DEFAULT_HOVER_BACKGROUND = new Color( 0, 0, 0, ( int ) ( 0.15 * 255 ) );
    private static final Color DEFAULT_FOREGROUND = Color.WHITE;
    private static final Color DEFAULT_BORDER_COLOR = new Color( 255, 255, 255, ( int ) ( 0.5 * 255 ) );
    private static final Font DEFAULT_FONT = new Font( "Roboto", Font.BOLD, 30 );

    private static final int Y_PADDING = 5;

    private String text;
    private Runnable action;

    private Color background;
    private Color pressBackground;
    private Color hoverBackground;
    private Color foreground;
    private Color borderColor;
    private Font font;

    private int width;

    private boolean hover;
    private boolean press;

    public Button ( String text, Runnable action, int width ) {
        this.text = text;
        this.action = action;

        background = DEFAULT_BACKGROUND;
        pressBackground = DEFAULT_PRESS_BACKGROUND;
        hoverBackground = DEFAULT_HOVER_BACKGROUND;
        foreground = DEFAULT_FOREGROUND;
        borderColor = DEFAULT_BORDER_COLOR;
        font = DEFAULT_FONT;

        this.width = width;

        hover = false;
        press = false;

        updateSize();
        addMouseListener( this );
    }

    @Override
    public void paint ( Graphics g ) {
        Graphics2D g2d = ( Graphics2D ) g;
        //activate antialiasing
        g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        //draw background, use border arc cause border area is filled as well
        RoundRectangle2D backgroundRect = new RoundRectangle2D.Float( 0, 0, getWidth(), getHeight(), BORDER_ARC_SIZE, BORDER_ARC_SIZE );
        //normal background
        g2d.setColor( background );
        g2d.fill( backgroundRect );
        if ( press ) {
            //clicked or pressed background
            g2d.setColor( pressBackground );
            g2d.fill( backgroundRect );
        } else if ( hover ) {
            //hovered background
            g2d.setColor( hoverBackground );
            g2d.fill( backgroundRect );
        }

        //draw border
        Area borderArea = createBorderArea( backgroundRect );
        g2d.setColor( borderColor );
        g2d.fill( borderArea );

        //draw text into the middle of the button
        g2d.setColor( foreground );
        g2d.setFont( font );
        //get width and height of the text, the "easy" way didn't work, don't know why
        int width = g2d.getFontMetrics().stringWidth( text );
        int height = ( int ) font.createGlyphVector( g2d.getFontMetrics().getFontRenderContext(), text ).getVisualBounds().getHeight();
        Dimension textBounds = new Dimension( width, height );
        int x = ( int ) ( ( getWidth() - textBounds.getWidth() ) / 2 );
        int y = ( int ) ( ( getHeight() + textBounds.getHeight() ) / 2 );
        g2d.drawString( text, x, y );

        g2d.dispose();
        revalidate();
    }

    private Area createBorderArea ( RoundRectangle2D backgroundRect ) {
        RoundRectangle2D innerRect = new RoundRectangle2D.Float( BORDER_SIZE, BORDER_SIZE, getWidth() - 2 * BORDER_SIZE,
                getHeight() - 2 * BORDER_SIZE, BACKGROUND_ARC_SIZE, BACKGROUND_ARC_SIZE );
        Area borderArea = new Area( backgroundRect );
        borderArea.subtract( new Area( innerRect ) );
        return borderArea;
    }

    private void updateSize () {
        int height = 2 * Y_PADDING + getFontMetrics( font ).getHeight();
        Dimension size = new Dimension( width, height );
        setPreferredSize( size );
        setMaximumSize( size );
        setMinimumSize( size );
        setSize( size );
    }

    private void performAction () {
        Thread actionThread = new Thread( action );
        actionThread.start();
    }

    @Override
    public void mousePressed ( MouseEvent e ) {
        if ( e.getButton() == MouseEvent.BUTTON1 ) {
            press = true;
            repaint();
        }
    }

    @Override
    public void mouseReleased ( MouseEvent e ) {
        if ( e.getButton() == MouseEvent.BUTTON1 ) {
            press = false;
            performAction();
            repaint();
        }
    }

    @Override
    public void mouseEntered ( MouseEvent e ) {
        hover = true;
        repaint();
    }

    @Override
    public void mouseExited ( MouseEvent e ) {
        hover = false;
        repaint();
    }

    public String getText () {
        return text;
    }

    public void setText ( String text ) {
        this.text = text;
        updateSize();
    }

    public Runnable getAction () {
        return action;
    }

    public void setAction ( Runnable action ) {
        this.action = action;
    }

    @Override
    public int getWidth () {
        return width;
    }

    public void setWidth ( int width ) {
        this.width = width;
        updateSize();
    }

    @Override
    public Color getBackground () {
        return background;
    }

    @Override
    public void setBackground ( Color background ) {
        this.background = background;
    }

    public Color getPressBackground () {
        return pressBackground;
    }

    public void setPressBackground ( Color pressBackground ) {
        this.pressBackground = pressBackground;
    }

    public Color getHoverBackground () {
        return hoverBackground;
    }

    public void setHoverBackground ( Color hoverBackground ) {
        this.hoverBackground = hoverBackground;
    }

    @Override
    public Color getForeground () {
        return foreground;
    }

    @Override
    public void setForeground ( Color foreground ) {
        this.foreground = foreground;
    }

    public Color getBorderColor () {
        return borderColor;
    }

    public void setBorderColor ( Color borderColor ) {
        this.borderColor = borderColor;
    }

    @Override
    public Font getFont () {
        return font;
    }

    @Override
    public void setFont ( Font font ) {
        this.font = font;
        updateSize();
    }

    //not used
    @Override
    public void mouseClicked ( MouseEvent e ) {}
}