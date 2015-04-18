package fitfame.common.dao.ibatis;

import java.io.Serializable;
import fitfame.common.exception.NonUniqueResultException;
import fitfame.common.exception.NotTableNameException;
import fitfame.common.page.PageInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * <p>
 * IBatis Dao的泛型基类.
 * 借鉴自SpringSide,按照约定写ibatis的xml文件,就可以将方法自动对应namespace和statementName
 * <p/>
 * <p>
 * 继承于Spring的SqlMapClientDaoSupport,提供分页函数和若干便捷查询方法，并对返回值作了泛型类型转换.
 * </p>
 * <p>
 * 冷水：增加了对表名的操作，用来进行自动分表
 * </p>
 * 
 * @author 欧雪映
 * @author weili.li
 * @author 冷水
 * @see SqlMapClientDaoSupport
 * @see PageInfo
 */
@SuppressWarnings("unchecked")
public class IBatisGenericDao extends SqlMapClientDaoSupport {

	protected static final String DOT = ".";
	protected static final String UNDERLINE = "_";

	protected static final String POSTFIX_INSERT = "insert";
	protected static final String POSTFIX_UPDATE = "update";
	protected static final String POSTFIX_DELETE = "delete";
	protected static final String POSTFIX_SELECT = "select";
	protected static final String PAGEQUERY_COUNT = "count";
	protected static final String POSTFIX_SELECT_ALL = "select_all";
	protected static final String POSTFIX_DELETE_ALL = "delete_all";
	private static final String PARAMNAME_CUSTOMTABLENAME = "tablename";
	private static final String PARAMNAME_PARAMETER = "params";

	private boolean useSimpleName = true;

	private boolean useNamespace = true;

	/**
	 * 在程序中是否定制表名，如果不在程序中定制表名则在sqlmap的xml文件中定义
	 */
	private boolean customTableName = false;

	public boolean isUseSimpleName() {
		return useSimpleName;
	}

	public void setUseSimpleName(boolean useSimpleName) {
		this.useSimpleName = useSimpleName;
	}

	public boolean isUseNamespace() {
		return useNamespace;
	}

	public void setUseNamespace(boolean useNamespace) {
		this.useNamespace = useNamespace;
	}

	/**
	 * 获得语句名,如果使用namespace,为namespace.statement.否则为statement
	 * 
	 * @param entityClass
	 * @param statementName
	 * @return
	 */
	protected String getSqlStatementName(Class entityClass, String statementName) {
		if (useNamespace)
			return getSqlMapNamespace(entityClass) + DOT + statementName;
		else
			return statementName;
	}

	/**
	 * 获得namespace名,使用简单命名方式为class名小写,否则为class全名
	 * 
	 * @param entityClass
	 * @return
	 */
	protected String getSqlMapNamespace(Class entityClass) {
		if (useSimpleName)
			return entityClass.getSimpleName();
		else
			return entityClass.getName();
	}

	/**
	 * 当设置 useAutoTableName=false时需要在子类重载此方法
	 * 
	 * @return
	 */
	protected String getTableName() {
		return null;
	}

	/**
	 * 获得参数对象，根据customTableName的设置返回
	 * 
	 * @param entityClass
	 * @param parameter
	 * @return
	 */
	protected Object getParameterObject(Object parameter) {
		if (customTableName) {
			Map<String, Object> result = new HashMap<String, Object>();
			String tablename = getTableName();
			if (tablename == null)
				throw new NotTableNameException();
			result.put(PARAMNAME_CUSTOMTABLENAME, tablename);
			result.put(PARAMNAME_PARAMETER, parameter);
			return result;
		} else {
			return parameter;
		}

	}

	/**
	 * 根据ID获取对象
	 * 
	 * @param entityClass
	 *            实体类
	 * @param id
	 *            主键
	 * @return 实体
	 */
	protected <T> T get(Class<T> entityClass, Serializable id) {

		T o = (T) getSqlMapClientTemplate().queryForObject(
				getSqlStatementName(entityClass, POSTFIX_SELECT),
				getParameterObject(id));
		if (o == null)
			throw new ObjectRetrievalFailureException(entityClass, id);
		return o;
	}

	/**
	 * 获取全部对象
	 * 
	 * @param entityClass
	 *            实体类
	 * @return 实体列表
	 */
	protected <T> List<T> getAll(Class<T> entityClass) {
		return getSqlMapClientTemplate().queryForList(
				getSqlStatementName(entityClass, POSTFIX_SELECT_ALL),
				getParameterObject(null));
	}

	/**
	 * 按参数查找
	 * 
	 * @param entityClass
	 *            实体类
	 * @param statementName
	 *            语句名
	 * @param parameterObject
	 *            参数类:Map,JavaBean
	 * @return 实体列表
	 */
	protected <T> List<T> findBy(Class<T> entityClass, String statementName,
			Object parameterObject) {
		return getSqlMapClientTemplate().queryForList(
				getSqlStatementName(entityClass, statementName),
				getParameterObject(parameterObject));
	}

	/**
	 * 按参数查找唯一
	 * 
	 * @param entityClass
	 *            实体类
	 * @param statementName
	 *            语句名
	 * @param parameterObject
	 *            参数类:Map,JavaBean
	 * @return 实体
	 */
	protected <T> T findUniqueBy(Class<T> entityClass, String statementName,
			Object parameterObject) {
		return uniqueElement(findBy(entityClass, statementName, parameterObject));
	}

	static <T> T uniqueElement(List<T> list) throws NonUniqueResultException {
		int size = list.size();
		if (size == 0)
			return null;
		T first = list.get(0);
		for (int i = 1; i < size; i++) {
			if (list.get(i) != first) {
				throw new NonUniqueResultException(list.size());
			}
		}
		return first;
	}

	/**
	 * 更新
	 * 
	 * @param entityClass
	 *            实体类
	 * @param statementName
	 *            语句名
	 * @param parameterObject
	 *            参数类:Map,JavaBean
	 * @return 影响的行数
	 */
	protected int updateBy(Class<?> entityClass, String statementName,
			Object parameterObject) {
		return getSqlMapClientTemplate().update(
				getSqlStatementName(entityClass, statementName),
				getParameterObject(parameterObject));
	}

	/**
	 * 新增对象
	 * 
	 * @param o
	 *            要保存的实体
	 * @return primary key
	 */
	public Object insert(Object o) {
		return getSqlMapClientTemplate().insert(
				getSqlStatementName(o.getClass(), POSTFIX_INSERT),
				getParameterObject(o));
	}

	/**
	 * 保存对象
	 * 
	 * @param o
	 *            要更新的实体
	 * @return rows affected
	 */
	public int update(Object o) {
		return getSqlMapClientTemplate().update(
				getSqlStatementName(o.getClass(), POSTFIX_UPDATE),
				getParameterObject(o));
	}

	/**
	 * 删除对象有返回值
	 * 
	 * @param o
	 *            要删除的实体
	 * @return rows affected
	 */
	public int delete(Object o) {
		int rows = getSqlMapClientTemplate().delete(
				getSqlStatementName(o.getClass(), POSTFIX_DELETE),
				getParameterObject(o));
		return rows;
	}

	/**
	 * 删除所有对象
	 * 
	 * @param entityClass
	 *            要删除的实体类
	 * @return rows affected
	 */
	public int deleteAll(Class<?> entityClass) {
		int rows = getSqlMapClientTemplate().delete(
				getSqlStatementName(entityClass, POSTFIX_DELETE_ALL),
				getParameterObject(null));
		return rows;
	}

	/**
	 * 逻辑层分页查询函数.
	 * 
	 * @param entityClass
	 *            实体类
	 * @param statementName
	 *            语句名
	 * @param parameterObject
	 *            参数类:Map,JavaBean
	 * @param page
	 *            分页对象
	 * @return 分页后的实体列表
	 */
	protected <T> List<T> pagedQuery(Class<T> entityClass,
			String statementName, Object parameterObject, PageInfo page) {
		return rawPagedQuery(getSqlStatementName(entityClass, statementName),
				parameterObject, page);
	}

	/**
	 * 物理层分页查询函数.
	 * 
	 * @param entityClass
	 *            实体类
	 * @param statementName
	 *            语句名
	 * @param parameterObject
	 *            参数类:Map,JavaBean
	 * @param page
	 *            分页对象
	 * @return 分页后的实体列表
	 */
	protected <T> List<T> pagedQueryInDB(Class<T> entityClass,
			String statementName, Object parameterObject, PageInfo page) {
		return rawPagedQueryInDB(getSqlStatementName(entityClass, statementName),
				parameterObject, page);
	}
	
	/**
	 * 逻辑层分页查询函数.
	 * 
	 * @param statementName
	 *            完整语句名(包括namespace)
	 * @param parameterObject
	 *            参数类:Map,JavaBean
	 * @param page
	 *            分页对象
	 * @return 分页后的实体列表
	 */
	public List rawPagedQuery(String statementName, Object parameterObject,
			PageInfo page) {

		Number totalCount = (Number) getSqlMapClientTemplate().queryForObject(
				statementName + UNDERLINE + PAGEQUERY_COUNT,
				getParameterObject(parameterObject));
		page.setTotalCount(totalCount.intValue());

		return getSqlMapClientTemplate().queryForList(statementName,
				getParameterObject(parameterObject),
				page.getCurrentPageStart(), page.getPageSize());
	}

	/**
	 * 物理层分页查询函数.
	 * 
	 * @param statementName
	 *            完整语句名(包括namespace)
	 * @param parameterObject
	 *            参数类:Map,JavaBean
	 * @param page
	 *            分页对象
	 * @return 分页后的实体列表
	 */
	public List rawPagedQueryInDB(String statementName, Object parameterObject,
			PageInfo page) {

		Number totalCount = (Number) getSqlMapClientTemplate().queryForObject(
				statementName + UNDERLINE + PAGEQUERY_COUNT,
				getParameterObject(parameterObject));
		page.setTotalCount(totalCount.intValue());

		return getSqlMapClientTemplate().queryForList(statementName,
				getParameterObject(parameterObject));
	}
	
	public void setCustomTableName(boolean customTableName) {
		this.customTableName = customTableName;
	}

	public boolean isCustomTableName() {
		return customTableName;
	}

}