package com.netfinworks.optimus.service.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.netfinworks.optimus.entity.ChanViewEntity;
import com.netfinworks.optimus.entity.ChanViewEntityExample;
import com.netfinworks.optimus.mapper.ChanViewEntityMapper;

@Repository
public class ChanViewRepository {

	@Autowired
	private ChanViewEntityMapper chanViewEntityMapper;

	/**
	 * 保存唯一的对象，如果存在就不保存了; 根据chanId 和date 唯一确定一条记录
	 * 
	 * @param entity
	 * @return
	 */
	public ChanViewEntity saveOrUpdate(ChanViewEntity entity) {
		if (StringUtils.isEmpty(entity.getId())) {
			entity.setId(UUID.randomUUID().toString());
		}
		ChanViewEntity exists = findByChanIdAndDate(entity.getChanId(),
				entity.getDate());
		if (exists != null) {
			entity.setId(exists.getId());
			chanViewEntityMapper.updateByPrimaryKey(entity);
			return entity;
		}
		entity.setCreateTime(new Date());
		chanViewEntityMapper.insert(entity);
		return entity;
	}

	/**
	 * 
	 * @param chanId
	 * @param date
	 * @return
	 */
	public ChanViewEntity findByChanIdAndDate(String chanId, Date date) {
		ChanViewEntityExample example = new ChanViewEntityExample();
		example.createCriteria().andChanIdEqualTo(chanId).andDateEqualTo(date);
		List<ChanViewEntity> results = chanViewEntityMapper
				.selectByExample(example);
		if (results.size() > 0) {
			return results.get(0);
		}
		return null;
	}
}
