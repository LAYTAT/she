package team.ecust.she.dao;

/**数据库接口，所有DAO的接口。*/
public interface IDao<K> {
	/**
	 * 数据库增加数据的抽象方法。
	 * @param k 需要往数据库对应表中添加的对象数组
	 * @return 执行结果，成功执行则返回true，否则返回false
	 */
	public abstract boolean saveItems(K k[]);
	
	/**
	 * 数据库删除数据的抽象方法。
	 * @param k 需要删除数据库对应表中的对象数组
	 * @return 执行结果，成功执行则返回true，否则返回false
	 */
	public abstract boolean removeItems(K k[]);
	
	/**
	 * 数据库修改数据的抽象方法。
	 * @param k 需要修改数据库对应表中的对象数组
	 * @return 执行结果，成功执行则返回true，否则返回false
	 */
	public abstract boolean updateItems(K k[]);
}