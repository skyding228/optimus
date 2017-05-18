/**
 * 
 */
package com.netfinworks.optimus.response;

import java.util.ArrayList;
import java.util.List;

import com.netfinworks.optimus.entity.OrderEntity;

/**
 * <p>注释</p>
 */
public class OrderQueryResponse extends PageQueryResponse {
    
    /**
     * 投标收益记录
     */
    private List<OrderEntity> result = new ArrayList<OrderEntity>();

	public List<OrderEntity> getResult() {
		return result;
	}

	public void setResult(List<OrderEntity> result) {
		this.result = result;
	}

       
}
