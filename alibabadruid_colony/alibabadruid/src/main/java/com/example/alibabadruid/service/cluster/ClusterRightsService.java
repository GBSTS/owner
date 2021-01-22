package com.example.alibabadruid.service.cluster;

import com.example.alibabadruid.dao.cluster.ClusterRightsDao;
import com.example.alibabadruid.entity.cluster.Rights;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClusterRightsService {

    @Resource
    private ClusterRightsDao clusterRightsDao;

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    public List<Rights> listAll() {
    	return clusterRightsDao.listAll();
    }


    /**
     * 根据主键查询
     *
     * @param rightid 主键
     * @return 返回记录，没有返回null
     */
    public Rights getById(Integer rightid) {
    	return clusterRightsDao.getById(rightid);
    }
	
    /**
     * 新增，插入所有字段
     *
     * @param rights 新增的记录
     * @return 返回影响行数
     */
    public int insert(Rights rights) {
    	return clusterRightsDao.insert(rights);
    }
	
    /**
     * 新增，忽略null字段
     *
     * @param rights 新增的记录
     * @return 返回影响行数
     */
    public int insertIgnoreNull(Rights rights) {
    	return clusterRightsDao.insertIgnoreNull(rights);
    }
	
    /**
     * 修改，修改所有字段
     *
     * @param rights 修改的记录
     * @return 返回影响行数
     */
    public int update(Rights rights) {
    	return clusterRightsDao.update(rights);
    }
	
    /**
     * 修改，忽略null字段
     *
     * @param rights 修改的记录
     * @return 返回影响行数
     */
    public int updateIgnoreNull(Rights rights) {
    	return clusterRightsDao.updateIgnoreNull(rights);
    }
	
    /**
     * 删除记录
     *
     * @param rights 待删除的记录
     * @return 返回影响行数
     */
    public int delete(Rights rights) {
    	return clusterRightsDao.delete(rights);
    }
	
}