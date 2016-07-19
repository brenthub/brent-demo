# brent-pattern

##设计模式的六大原则
	1、开闭原则（Open Close Principle）：对扩展开放对修改关闭
	2、里氏代换原则（Liskov Substitution Principle）：父类出现的地方，子类也可出现
	3、依赖倒转原则（Dependence Inversion Principle）：依赖抽象而不依赖具体
	4、接口隔离原则（Interface Segregation Principle）：多个隔离的接口，比使用单个接口要好
	5、迪米特法则（最少知道原则）（Demeter Principle）：最少知道原则。一个实体应当尽量少的与其他实体之间发生相互作用
	6、合成复用原则（Composite Reuse Principle）：尽量使用合成/聚合的方式，而不是使用继承。
	
	
	
##创建型模式(五种)
	工厂方法模式(Factory)：工厂创建对象
	抽象工厂模式(Abstractfactory)：抽象工厂实例创建对象，工厂可修改，灵活度高
	单例模式(Singleton)：适用于只需要一个对象的情况
	建造者模式(Builder)：创建复杂对象
	原型模式(Prototype)：复制对象，包括深度复制和浅度复制，深度复制重建引用对象，浅度复制不创建



##结构型模式(七种)
	适配器模式(Adapter)：开发阶段不考虑，适用于后期维护时添加功能
	装饰器模式(Decorator)：扩展方法功能，可在方法执行前后加代码
	代理模式(Proxy)：调用引用对象方法实现自身功能
	外观模式(Facade)：CPU，内存，硬盘组装成电脑。组装多个对象实现自身功能。
	桥接模式(Bridge)：通过setXxx方法任意修改引用实例，调用同样方法实现不同功能
	组合模式(Composite)：部分与整体，常用于表示树形结构
	享元模式(Flyweight)：维护资源集合，经典案例：数据库连接池，避免重新开启数据库链接的开销



##行为型模式(十一种)
	策略模式(Strategy)：定义多个不同的实现类，这些类实现公共接口，通过调用接口调用不同实例得到不同结果
	模板方法模式(Template)：父类定义公共方法，不同子类重写父类抽象方法，得到不同结果
	观察者模式(Observer)：目标方法被调用，通知所有观察者
	迭代子模式(Interator)：迭代器实现原理
	责任链模式(ChainOfResponsibility)：依次引用，依次执行
	命令模式(Commond)：各司其职，逐层调用，有点像三层架构
	备忘录模式(Memento)：建立原始对象副本，用于存储恢复原始对象数据
	状态模式(Stage)：通过改变状态，改变行为
	访问者模式(Visitor)：结构与操作解耦。灵活的操作，放入固定的结构中执行
	中介者模式(Mediator)：中介类维护对象行为。主程序直接调用中介对象即可
	解释器模式(Iterpreter)：定义解释类，解释包装对象
	

