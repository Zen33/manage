package fitfame.common.dao.ibatis;

import java.io.Serializable;
import fitfame.common.page.PageInfo;
import fitfame.common.util.GenericsUtils;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 负责为单个Entity 提供CRUD操作的IBatis DAO基类. 子类只要在类定义时指定所管理Entity的Class,
 * 即拥有对单个Entity对象的CRUD操作.
 * </p>
 * 
 * <pre>
 * public class UserDao extends IBatisEntityDao&lt;User&gt; {
 * }
 * </pre>
 * 
 * @author suwei
 * @author weili.li
 * @author 冷水
 * @see IBatisGenericDao
 */
public class IBatisEntityDao<T> extends IBatisGenericDao {

	/**
	 * DAO所管理的Entity类型.
	 */
	protected Class<T> entityClass;

	protected String primaryKeyName;

	/**
	 * 在构造函数中将泛型T.class赋给entityClass.
	 */
	@SuppressWarnings("unchecked")
	public IBatisEntityDao() {
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 查找
	 */
	public List<T> findBy(String statementName, Object parameterObject) {
		return findBy(getEntityClass(), statementName, parameterObject);
	}

	/**
	 * 查找唯一
	 */
	public T findUniqueBy(String statementName, Object parameterObject) {
		return findUniqueBy(getEntityClass(), statementName, parameterObject);
	}

	/**
	 * 更新
	 */
	public int updateBy(String statementName, Object parameterObject) {
		return updateBy(getEntityClass(), statementName, parameterObject);
	}

	/**
	 * 根据ID获取对象.
	 */
	public T get(Serializable id) {
		return get(getEntityClass(), id);
	}

	/**
	 * 获取全部对象.
	 */
	public List<T> getAll() {
		return getAll(getEntityClass());
	}

	/**
	 * 删除所有对象
	 */
	public int deleteAll() {
		return deleteAll(getEntityClass());
	}

	/**
	 * 取得entityClass.
	 * <p/>
	 * JDK1.4不支持泛型的子类可以抛开Class<T> entityClass,重载此函数达到相同效果。
	 */
	protected Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * 逻辑层分页查询.
	 */
	public List<T> pagedQuery(String statementName, Object parameterObject,
			PageInfo page) {
		return pagedQuery(getEntityClass(), statementName, parameterObject,
				page);
	}

	/**
	 * 物理层分页查询.
	 */
	public List<T> pagedQueryInDB(String statementName, Map<String, Object> parameterObject,
			PageInfo page) {
		parameterObject.put("FromItemNo", page.getCurrentPageStart());
		parameterObject.put("ToItemNo", page.getPageSize());
		return pagedQueryInDB(getEntityClass(), statementName, parameterObject,
				page);
	}
}