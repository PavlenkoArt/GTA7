import javax.swing.*;
import java.awt.*;

public class Player  extends  GameObject{

    Handler handler;
    Game game;
    private JFrame frame;

    public Player(int x, int y, ID id, Handler handler, Game game,JFrame frame) {
        super(x, y, id);
        this.handler = handler;
        this.game = game;
        this.frame = frame;
    }


    public void tick() {
        x += velX;
        y += velY;

        collision();

        if(handler.isUp()) velY = -5;
        else if(!handler.isDown()) velY = 0;

        if(handler.isDown()) velY = 5;
        else if(!handler.isUp()) velY = 0;

        if(handler.isRight()) velX = 5;
        else if(!handler.isLeft()) velX = 0;

        if(handler.isLeft()) velX = -5;
        else if(!handler.isRight()) velX = 0;

        if (game.hp <= 0) {
            game.stopGame();
            JButton restartButton = new JButton("Начать заново");
            restartButton.addActionListener(e -> game.restart());
            frame.add(restartButton);
        }
    }

    private void collision() {
        for (int i = 0; i < handler.object.size(); i++) {

            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Block) {
                if (getBounds().intersects(tempObject.getBounds())) {

                    x += velX * -1;
                    y += velY * -1;

                }
            }
            if (tempObject.getId() == ID.Crate) {
                if (getBounds().intersects(tempObject.getBounds())) {

                    game.ammo += 10;
                    handler.removeObject(tempObject);

                }
            }
            if (tempObject.getId() == ID.Enemy) {
                if (getBounds().intersects(tempObject.getBounds())) {

                    game.hp--;

                }
            }
        }
    }

        public void render (Graphics g){
            g.setColor(Color.blue);
            g.fillRect(x, y, 32, 32);
        }


        public Rectangle getBounds () {
            return new Rectangle(x, y, 32, 48);
        }
    }
