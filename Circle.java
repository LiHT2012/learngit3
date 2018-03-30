package com.dbcool.api.action;

public class Circle {
    private double radius = 0;
    public static int count =1;

    private Draw Draw = null;

    public Circle(double radius) {
        this.radius = radius;
        new Draw().drawSahpe();//外部访问内部类需先new对象
    }

    public Circle() {

	}
    public Draw getInnerInstance() {
        if(Draw == null)
        	Draw = new Draw();
        return Draw;
    }
/**
 * 内部类可以拥有private访问权限、protected访问权限、public访问权限及包访问权限。
 * 比如上面的例子，如果成员内部类Inner用private修饰，则只能在外部类的内部访问，如果用public修饰，则任何地方都能访问；
 * 如果用protected修饰，则只能在同一个包下或者继承外部类的情况下访问；如果是默认访问权限，则只能在同一个包下访问。
 * 这一点和外部类有一点不一样，外部类只能被public和包访问两种权限修饰。我个人是这么理解的，
 * 由于成员内部类看起来像是外部类的一个成员，所以可以像类的成员一样拥有多种权限修饰
 *
 */
	class Draw {     //成员内部类
    	private double radius = 2;

		public void drawSahpe() {
      int radius = 3;
      System.out.println(radius);//3
        	System.out.println(this.radius); //2与外部类成员同名时，默认访问自身的，同名方法同理
            System.out.println(Circle.this.radius);  //1外部类的private成员
            System.out.println(count);   //外部类的静态成员
        }

    }
    public static void main (String[] args) {
    	//成员内部类是依附外部类而存在的，也就是说，如果要创建成员内部类的对象，前提是必须存在一个外部类的对象。
    	//创建成员内部类对象的一般方式如下：
    	//第一种方式：
    	Circle outter = new Circle();
    	Circle.Draw inner = outter.new Draw();  //必须通过外部类对象来创建

        //第二种方式：
    	Circle.Draw inner1 = outter.getInnerInstance();
    }
    /**
     * 局部内部类是定义在一个方法或者一个作用域里面的类，它和成员内部类的区别在于局部内部类的访问仅限于方法内或者该作用域内。
     * 局部内部类就像是方法里面的一个局部变量一样，是不能有public、protected、private以及static修饰符的
     */
    /**
     * 匿名内部类应该是平时我们编写代码时用得最多的，在编写事件监听的代码时使用匿名内部类不但方便，而且使代码更加容易维护。
     * 使用匿名内部类能够在实现父类或者接口中的方法情况下同时产生一个相应的对象，但是前提是这个父类或者接口必须先存在才能这样使用.
     * 匿名内部类是唯一一种没有构造器的类。正因为其没有构造器，所以匿名内部类的使用范围非常有限，大部分匿名内部类用于接口回调。
     * 匿名内部类在编译的时候由系统自动起名为Outter$1.class。
     * 一般来说，匿名内部类用于继承其他类或是实现接口，并不需要增加额外的方法，只是对继承方法的实现或是重写。
     */
    /**
     * 静态内部类也是定义在另一个类里面的类，只不过在类的前面多了一个关键字static。静态内部类是不需要依赖于外部类的，
     * 这点和类的静态成员属性有点类似，并且它不能使用外部类的非static成员变量或者方法，这点很好理解，因为在没有外部类的对象的情况下，
     * 可以创建静态内部类的对象，如果允许访问外部类的非static成员就会产生矛盾，因为外部类的非static成员必须依附于具体的对象。
     */
}
