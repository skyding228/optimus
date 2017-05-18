/**
 * 
 */
package com.netfinworks.optimus.response;

import java.util.ArrayList;
import java.util.List;

import com.netfinworks.optimus.entity.EntryEntity;

/**
 * <p>注释</p>
 */
public class EntryQueryResponse extends PageQueryResponse {
    
    /**
     * 投标收益记录
     */
    private List<EntryEntity> result = new ArrayList<EntryEntity>();

	public List<EntryEntity> getResult() {
		return result;
	}

	public void setResult(List<EntryEntity> result) {
		this.result = result;
	}

       
}
