package com.web.comment.unit;/** * 错误码说明 * 0=正常 */public interface SysExcCode {    /***     * 通用异常码定义     * 成功  0     * 失败 -1     *     */    class SysCommonExcCode {        public final static int SYS_SUCCESS = 0;        public final static int SYS_ERROR = - 1;    }}