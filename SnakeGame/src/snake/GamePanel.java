package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener{
    private int len; // ����̰���ߵĳ���
    private int[] snakeX = new int[35]; // �����ߵĺ�����x
    private int[] snakeY = new int[28]; // �����ߵ�������y
    private String dir = "R";// ��ͷ����:R�����ң�D:���£�L������U������
    private boolean isStart = false;// ��ʾ̰������Ϸ�Ƿ�ʼ����ʼ��Ϊ��û��ʼ
    //javax.swing.Timer timer = new javax.swing.Timer(200, this);// ��ʱ������һ����������ʱִ���¼�
    private int foodX;// ����ʳ��ĺ�����
    private int foodY;// ����ʳ���������
    private int score;// ������Ϸ�÷�
    private Random random = new Random();// �������������
    private boolean isOver = false;// ��ʾ��Ϸ�Ƿ�ʧ�ܣ�����
    // ��ʼ�����ϱ�������
    public GamePanel() {
        Runnable task = ()->{
            try {
                while(true)
                {
                    change();
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                System.out.println(1);
            }
        };
        new Thread(task).start();
        Init();
        this.setFocusable(true);// ��ȡ�����¼�
        this.addKeyListener(this);// ��Ӽ��̼����¼�
    }
    public void change()
    {
        if(isStart == true && isOver == false) {
            // �����ͷ����ʳ��λ�ã����ʳ��Ե�
            if (snakeX[0] == foodX && snakeY[0] == foodY) {
                len++; // �ߵĳ��ȼ�һ
                score += 10;// ������10
                // ����ʳ������
                foodX = 50 + 25 * random.nextInt(32);
                foodY = 100 + 25 * random.nextInt(24);
            }
            //���ƣ��ú�һ���Ƶ�ǰһ����λ�ü���
            for (int i = len-1; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }
            // ̰����ͷ�����հ������Ƶķ����ƶ�
            if (dir.equals("R")) {
                snakeX[0] = snakeX[0] + 25;// ��ͷ�����ƶ�һ����λ��25Ϊһ����λ
                if (snakeX[0] >= 900) {
                    isOver = true;// ̰����ײǽ����Ϸʧ��
                }
            } else if (dir.equals("D")) {
                snakeY[0] = snakeY[0] + 25;
                if (snakeY[0] >= 800) {
                    isOver = true;
                }
            } else if (dir.equals("L")) {
                snakeX[0] = snakeX[0] - 25;
                if (snakeX[0] <= 0) {
                    isOver = true;
                }
            } else if (dir.equals("U")) {
                snakeY[0] = snakeY[0] - 25;
                if (snakeY[0] <= 0) {
                    isOver = true;
                }
            }

            // ѭ���ж���ͷ�Ƿ�ײ������
            for (int i = 1; i < len; i++) {
                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                    isOver = true;// �����ͷײ����������Ϸ����
                }
            }
            repaint();// ���ϵĸ�����Ϸ����ҳ�棬ʵ�ֶ���
        }
    }
    // ��ʼ����Ϸ����
    public void Init() {
        len = 3;// ��Ϸ��ʼǰ��̬�����ϣ�������ͷ�������ڣ��������ڣ����߳���Ϊ3
        // ��ʼ����ͷλ��
        snakeX[0] = 200;
        snakeY[0] = 200;
        // ��ʼ����һ������λ��
        snakeX[1] = 175;
        snakeY[1] = 200;
        // ��ʼ���ڶ�������λ��
        snakeX[2] = 150;
        snakeY[2] = 200;
        dir = "R";// ��ʼ����ͷ��������
        // �������ʳ������
        foodX = 50 + 25 * random.nextInt(32);
        foodY = 100 + 25 * random.nextInt(25);
        // ��Ϸ���ֳ�ʼ��Ϊ0
        score = 0;
    }

    // �����
    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g); // ����Ч��
        this.setBackground(Color.gray); // �������ı�����ɫ
        // ������Ϸ���ְ��
        g.setColor(Color.RED);
        g.setFont(new Font("����", Font.BOLD, 20));
        g.drawString("���ȣ�" + len, 680, 30);
        g.drawString("������" + score, 680, 55);
        ImageData.food.paintIcon(this, g, foodX, foodY);// ��������������ʳ��
        // ������ͷ����λ��
        if (dir.equals("R")) {// ����ͷ����ʱ
            ImageData.right.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (dir.equals("D")) {// ����ͷ����ʱ
            ImageData.down.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (dir.equals("L")) {// ����ͷ����ʱ
            ImageData.left.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (dir.equals("U")) {// ����ͷ����ʱ
            ImageData.up.paintIcon(this, g, snakeX[0], snakeY[0]);
        }
        // ��������
        for (int i = 1; i < len; i++) {
            ImageData.body.paintIcon(this, g, snakeX[i], snakeY[i]);
        }
        // ��Ϸ��ʼǰ����ʾ
        if (isStart == false) {
            g.setColor(Color.orange);
            g.setFont(new Font("����", Font.BOLD, 40));
            g.drawString("���¿ո����ʼ��Ϸ", 245, 400);
            g.drawString("��һ��ʳ���10��", 265, 450);
        }
        // ��Ϸʧ�ܣ�����
        if (isOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("����", Font.BOLD, 40));
            g.drawString("��Ϸʧ�ܣ����¿ո�������¿�ʼ��Ϸ", 100, 400);
            g.drawString("��һ��ʳ���10��", 260, 450);
        }
    }

    // ���̼����¼�:����������Ϸ�Ƿ�ʼ
    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        // ���̰���δ�ͷ�
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            if (isOver) {// ����Ϸʧ��ʱ���¿�ʼ��Ϸ
                isOver = false;
                Init();
                // new GamePanel();//���½�����Ϸ
            } else {// ����ϷΪ����ʱ�����¿ո����ͣ��Ϸ
                // ������¿ո��
                isStart = !isStart;// ������Ϸ����ȡ��
            }
            //System.out.println(isStart);
            repaint();// ���»��ƽ���
        }
        // �����������ҿ���̰���ߵ�ͷ������
        if (keyCode == KeyEvent.VK_RIGHT) {
            if (dir.equals("L") == false) {
                dir = "R";// ����ߵ�ǰ�ƶ��ķ���������ʱ������������ƶ�
            }
        } else if (keyCode == KeyEvent.VK_DOWN) {
            if (dir.equals("U") == false) {
                dir = "D";// ����ߵ�ǰ�ƶ��ķ���������ʱ������������ƶ�
            }
        } else if (keyCode == KeyEvent.VK_LEFT) {
            if (dir.equals("R") == false) {
                dir = "L";// ����ߵ�ǰ�ƶ��ķ���������ʱ������������ƶ�
            }
        } else if (keyCode == KeyEvent.VK_UP) {
            if (dir.equals("D") == false) {
                dir = "U";// ����ߵ�ǰ�ƶ��ķ���������ʱ������������ƶ�
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
