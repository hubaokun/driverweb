/**
 * 
 */
package com.daoshun.guangda.service;


/**
 * @author wangcl
 * 
 */
public interface IBaseService {

	/**
	 * 文件地址
	 * 
	 * @param fileid
	 * @return fileurl
	 */
	public abstract String getFilePathById(long id);

	public abstract long uploadComplete(String fileurl, String filename);

	public abstract String[] intersect(Object[] arr1, Object[] arr2);

	public abstract String[] minus(String[] arr1, String[] arr2);

}
