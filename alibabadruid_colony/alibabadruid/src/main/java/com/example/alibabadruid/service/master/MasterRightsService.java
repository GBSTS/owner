package com.example.alibabadruid.service.master;

import com.example.alibabadruid.dao.master.MasterRightsDao;
import com.example.alibabadruid.entity.master.Rights;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MasterRightsService {

    @Resource
    private MasterRightsDao masterRightsDao;

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    public List<Rights> listAll() {
    	return masterRightsDao.listAll();
    }


    /**
     * 根据主键查询
     *
     * @param rightid 主键
     * @return 返回记录，没有返回null
     */
    public Rights getById(Integer rightid) {
    	return masterRightsDao.getById(rightid);
    }
	
    /**
     * 新增，插入所有字段
     *
     * @param rights 新增的记录
     * @return 返回影响行数
     */
    public int insert(Rights rights) {
    	return masterRightsDao.insert(rights);
    }
	
    /**
     * 新增，忽略null字段
     *
     * @param rights 新增的记录
     * @return 返回影响行数
     */
    public int insertIgnoreNull(Rights rights) {
    	return masterRightsDao.insertIgnoreNull(rights);
    }
	
    /**
     * 修改，修改所有字段
     *
     * @param rights 修改的记录
     * @return 返回影响行数
     */
    public int update(Rights rights) {
    	return masterRightsDao.update(rights);
    }
	
    /**
     * 修改，忽略null字段
     *
     * @param rights 修改的记录
     * @return 返回影响行数
     */
    public int updateIgnoreNull(Rights rights) {
    	return masterRightsDao.updateIgnoreNull(rights);
    }
	
    /**
     * 删除记录
     *
     * @param rights 待删除的记录
     * @return 返回影响行数
     */
    public int delete(Rights rights) {
    	return masterRightsDao.delete(rights);
    }
	
}