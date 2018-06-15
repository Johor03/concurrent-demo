public class FutureTest {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.err.println(">>>>>>>>>>>>>>>> 执行开始时间 ...."  + start);
		ExecutorService pool = Executors.newFixedThreadPool(5);
		TaskA t1 = new TaskA(3000);
		TaskB t2 = new TaskB(3000);
		TaskC t3 = new TaskC(3000);

		FutureTask<Integer> f1 = new FutureTask<>(t1);
		FutureTask<BigDecimal> f2 = new FutureTask<>(t2);
		FutureTask<Person> f3 = new FutureTask<>(t3);

		pool.execute(f1);
		pool.execute(f2);
		pool.execute(f3);

		while (true) {
			if (f1.isDone() && f2.isDone() && f3.isDone()) {// 任务都完成
				System.out.println("Done");
				pool.shutdown(); // 关闭线程池和服务
				break;
			}
		}
		try {
			//模拟三个线程并发执行后汇总聚合结果
			System.out.println("任务信息A：" + f1.get() + "  ~~~ 任务信息B:"  + f2.get() + "  ~~~ 任务信息C：" + f3.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		System.err.println(">>>>>>>>>>>>>>>>  执行耗时...." + (System.currentTimeMillis() - start));
	}

}

class TaskA implements Callable<Integer> {

	int mills;

	public TaskA(int millis) {
		this.mills = millis;
	}

	@Override
	public Integer call() throws Exception {
		Thread.sleep(mills);
		return 25;
	}

}

class TaskB implements Callable<BigDecimal> {

	int mills;

	public TaskB(int millis) {
		this.mills = millis;
	}

	@Override
	public BigDecimal call() throws Exception {
		Thread.sleep(mills);
		return new BigDecimal(336.62);
	}

}

class TaskC implements Callable<Person> {

	int mills;

	public TaskC(int millis) {
		this.mills = millis;
	}

	@Override
	public Person call() throws Exception {
		Person p = new Person();
		p.setName("石头");
		p.setAccount(BigDecimal.ZERO);
		p.setRoleId(33333);
		Thread.sleep(mills);
		return p;
	}

}

class Person {
	private String name;
	private Integer roleId;
	private BigDecimal account;

	@Override
	public String toString() {
		return "Person [name=" + name + ", roleId=" + roleId + ", account=" + account + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

}
