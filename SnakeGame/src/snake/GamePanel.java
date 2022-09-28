package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener{
    private int len; // 定义贪吃蛇的长度
    private int[] snakeX = new int[35]; // 定义蛇的横坐标x
    private int[] snakeY = new int[28]; // 定义蛇的纵坐标y
    private String dir = "R";// 蛇头方向:R：向右，D:向下，L：向左，U：向上
    private boolean isStart = false;// 表示贪吃蛇游戏是否开始，初始化为还没开始
    //javax.swing.Timer timer = new javax.swing.Timer(200, this);// 定时器，第一个参数：定时执行事件
    private int foodX;// 定义食物的横坐标
    private int foodY;// 定义食物的纵坐标
    private int score;// 定义游戏得分
    private Random random = new Random();// 创建随机数对象
    private boolean isOver = false;// 表示游戏是否失败，结束
    // 初始化以上变量数据
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
        this.setFocusable(true);// 获取焦点事件
        this.addKeyListener(this);// 添加键盘监听事件
    }
    public void change()
    {
        if(isStart == true && isOver == false) {
            // 如果蛇头到达食物位置，则把食物吃掉
            if (snakeX[0] == foodX && snakeY[0] == foodY) {
                len++; // 蛇的长度加一
                score += 10;// 分数加10
                // 更新食物坐标
                foodX = 50 + 25 * random.nextInt(32);
                foodY = 100 + 25 * random.nextInt(24);
            }
            //右移：让后一个移到前一个的位置即可
            for (int i = len-1; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }
            // 贪吃蛇头部按照按键控制的方向移动
            if (dir.equals("R")) {
                snakeX[0] = snakeX[0] + 25;// 蛇头向右移动一个单位：25为一个单位
                if (snakeX[0] >= 900) {
                    isOver = true;// 贪吃蛇撞墙，游戏失败
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

            // 循环判断蛇头是否撞到蛇身
            for (int i = 1; i < len; i++) {
                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                    isOver = true;// 如果蛇头撞到蛇身则游戏结束
                }
            }
            repaint();// 不断的更新游戏区域页面，实现动画
        }
    }
    // 初始化游戏数据
    public void Init() {
        len = 3;// 游戏开始前静态界面上，加上蛇头，有三节（蛇身两节）即蛇长度为3
        // 初始化蛇头位置
        snakeX[0] = 200;
        snakeY[0] = 200;
        // 初始化第一个蛇身位置
        snakeX[1] = 175;
        snakeY[1] = 200;
        // 初始化第二个蛇身位置
        snakeX[2] = 150;
        snakeY[2] = 200;
        dir = "R";// 初始化蛇头方向向右
        // 随机生成食物坐标
        foodX = 50 + 25 * random.nextInt(32);
        foodY = 100 + 25 * random.nextInt(25);
        // 游戏积分初始化为0
        score = 0;
    }

    // 画面板
    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g); // 清屏效果
        this.setBackground(Color.gray); // 设置面板的背景颜色
        // 绘制游戏积分板块
        g.setColor(Color.RED);
        g.setFont(new Font("宋体", Font.BOLD, 20));
        g.drawString("长度：" + len, 680, 30);
        g.drawString("分数：" + score, 680, 55);
        ImageData.food.paintIcon(this, g, foodX, foodY);// 根据随机坐标绘制食物
        // 控制蛇头方向及位置
        if (dir.equals("R")) {// 当蛇头向右时
            ImageData.right.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (dir.equals("D")) {// 当蛇头向下时
            ImageData.down.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (dir.equals("L")) {// 当蛇头向左时
            ImageData.left.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (dir.equals("U")) {// 当蛇头向上时
            ImageData.up.paintIcon(this, g, snakeX[0], snakeY[0]);
        }
        // 绘制蛇身
        for (int i = 1; i < len; i++) {
            ImageData.body.paintIcon(this, g, snakeX[i], snakeY[i]);
        }
        // 游戏开始前的提示
        if (isStart == false) {
            g.setColor(Color.orange);
            g.setFont(new Font("宋体", Font.BOLD, 40));
            g.drawString("按下空格键开始游戏", 245, 400);
            g.drawString("吃一个食物得10分", 265, 450);
        }
        // 游戏失败，结束
        if (isOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("宋体", Font.BOLD, 40));
            g.drawString("游戏失败，按下空格键则重新开始游戏", 100, 400);
            g.drawString("吃一个食物得10分", 260, 450);
        }
    }

    // 键盘监听事件:按键控制游戏是否开始
    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        // 键盘按下未释放
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            if (isOver) {// 当游戏失败时重新开始游戏
                isOver = false;
                Init();
                // new GamePanel();//重新进入游戏
            } else {// 当游戏为结束时，按下空格键暂停游戏
                // 如果按下空格键
                isStart = !isStart;// 控制游戏开关取反
            }
            //System.out.println(isStart);
            repaint();// 重新绘制界面
        }
        // 按键上下左右控制贪吃蛇的头部方向
        if (keyCode == KeyEvent.VK_RIGHT) {
            if (dir.equals("L") == false) {
                dir = "R";// 如果蛇当前移动的方向不是向左时，则可以向右移动
            }
        } else if (keyCode == KeyEvent.VK_DOWN) {
            if (dir.equals("U") == false) {
                dir = "D";// 如果蛇当前移动的方向不是向上时，则可以向下移动
            }
        } else if (keyCode == KeyEvent.VK_LEFT) {
            if (dir.equals("R") == false) {
                dir = "L";// 如果蛇当前移动的方向不是向右时，则可以向左移动
            }
        } else if (keyCode == KeyEvent.VK_UP) {
            if (dir.equals("D") == false) {
                dir = "U";// 如果蛇当前移动的方向不是向右时，则可以向左移动
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
