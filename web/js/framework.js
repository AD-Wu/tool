// ======================================= AX-JS 框架说明 ====================================
// AX-JS 框架对象文件
// @author: Ardon Chan
// @email: ardon.chan@gmail.com
// @QQ: 25409556
//
// 可以使用 $SYS_IGNORE_LOGIN 标识设置是否不检查是否登录（true/false）
// ========================================================================================

/**
 * AX-JS 框架公共对象
 */
var $G = new $Global();

/**
 * 窗口元素操作对象
 */
var $W = new $Window();

/**
 * 系统配置对象
 */
function $Config() {
    /**
     * 是否调试模式
     */
    this.debugMode = true;
    /**
     * 调试窗口高度
     */
    this.debugerHeight = 200;
    /**
     * 默认多国语言标识
     */
    this.localKey = "zh_CN";
    /**
     * 图片文件夹（含开始及结束目录符）
     */
    this.imagesFolder = "/global/images/";
    /**
     * 模块文件夹（含开始及结束目录符）
     */
    this.modulesFolder = "/global/modules/";
    /**
     * 样式库文件夹（含开始及结束目录符）
     */
    this.themesFolder = "/global/themes/";
    /**
     * 接口地址
     */
    this.protocolUrl = "/api/json";
}

/**
 * AX-JS 框架公共对象
 */
function $Global() {

    /**
     * 全局语言对象
     */
    this.globalText = {
        en_US: {
            // Debug text
            loadingPage: "Loading page: {0}",
            sendInfo: "Send request: {0}:{1}[version:{2}] > {3}",
            recvInfo: "Receive data: {0}:{1}[version:{2}] < [{3}] - status: {4}, message: \"{5}\", data: {6}",
            dataError: "Data error: {0}, stack: {1}",
            // Dialog text
            title: "System Title",
            maximize: "Maximize",
            restore: "Restore",
            close: "Close",
            ok: "Ok",
            cancel: "Cancel",
            yes: "Yes",
            yesToAll: "Yes to all",
            no: "No",
            noToAll: "No to all",
            data: "Data",
            readOnly: "Read only",
            get: "Get {0}",
            add: "Add {0}",
            edit: "Edit {0}",
            deletes: "Delete {0}",
            deleteBatch: "Delete {0}s",
            deleteAll: "Delete all {0}s",
            deleteNote: "Note: this action cannot be undone",
            count: "Get the total number of {0}",
            list: "Get list data of {0}",
            page: "Get {0} data by paging",
            update: "Update {0} data",
            noChanges: "Data has not been modified",
            acceptDrop: "Select one from the drop-down list",
            acceptReg: "Acceptable {0} to {1} letters({2})",
            regWords: "words, numbers",
            regNumber: "numbers",
            regSperator: ", ",
            acceptString: "Acceptable {0} to {1} letters",
            acceptPassword: "Acceptable {0} to {1} letters",
            acceptNumber: "Data valid range from {0} to {1}",
            dataInvalid: "{0} is invalid, please re-entry.",
            dataNotMatch: "Data format does not match",
            notSupport: "The browser does not support asynchronous data transfer",
            show: "Show",
            hide: "Hide",
            refresh: "Refresh",
            loadPage: "Load page",
            loading: "Process data...",
            loadingData: "Loading data...",
            loadingSuccess: "Load data complete",
            loadingFailed: "Load data failed!",
            // Data text
            executing: "{0}",
            operation: "Operation",
            executeSending: "Sending request to the server...",
            updateSuccess: "Update data success",
            updateFailed: "Update data failed!",
            dataNotExist: "Requested data does not exist",
            confirm: "{0}?",
            confirmFailed: "{0} failed, continue next?",
            confirmDetail: "Remaining {0} data(s), details: {1}",
            confirmCancel: "Are you sure you want to interrupt current operation?",
            confirmCancelNote: "Request has been executed will not be canceled",
            canceling: "Waiting for stopping...",
            canceled: "{0} was interrupted",
            success: "{0} success",
            failedDetail: "{0} is not entirely successful",
            total: "total: {0}",
            status1: "{0} success{2}",
            status2: "{0} failed, execute method error.{2}",
            status3: "{0} failed, request timeout.{2}",
            status4: "{0} failed, data invalid.{2}",
            status5: "{0} failed, login required.{2}",
            status6: "{0} failed, authorization required.{2}",
            status7: "{0} failed, data processing exception.{2}",
            status8: "{0} failed.{2}",
            // Datagrid text
            dataOption: "Options",
            searcher: "Advance search",
            index: "ID",
            choose: "Select",
            totalCount: "Total",
            loaded: "Load",
            status: "Status",
            search: "Search",
            searchClear: "Clear search",
            none: "none",
            sorting: "Data sorting...",
            sortComplete: "Data sorting is done",
            searchRequire: "Please enter the search content",
            editorInputTips: "Use \"Up/Down\" or \"CTRL + Left/Right\" to change the cell",
            editorTextTips: "Use \"CTRL + Up/Down/Left/Right\" to change the cell",
            editorSave: "Do you want to save the editing data?",
            needsRow: "Please select a row first",
            oneRowOnly: "Please select only one row"
        },
        // TODO: 国际化
        zh_CN: {
            // Debug text
            loadingPage: "加载页面: {0}",
            sendInfo: "发送请求: {0}:{1}[version:{2}] > {3}",
            recvInfo: "接收数据: {0}:{1}[version:{2}] < [{3}] - status: {4}, message: \"{5}\", data: {6}",
            dataError: "数据错误：{0}, stack: {1}",
            // Dailog text
            title: "系统标题",
            maximize: "最大化",
            restore: "还原",
            close: "关闭",
            ok: "确定",
            cancel: "取消",
            yes: "是",
            yesToAll: "全部是",
            no: "否",
            noToAll: "全部否",
            data: "数据",
            readOnly: "只读",
            get: "获取{0}",
            add: "增加{0}",
            edit: "编辑{0}",
            deletes: "删除{0}",
            deleteBatch: "批量删除{0}",
            deleteAll: "删除全部{0}",
            deleteNote: "注意：数据删除后将无法恢复",
            count: "获取{0}总数",
            list: "获取{0}列表",
            page: "获取{0}分页",
            update: "更新{0}数据",
            noChanges: "数据未被编辑",
            acceptDrop: "请从下拉框列表内选择一项",
            acceptReg: "请输入 {0} 到 {1} 个字符（可包含：{2}）",
            regWords: "英文数字",
            regNumber: "数字",
            regSperator: "",
            acceptString: "请输入 {0} 到 {1} 个字符或 {2} 个汉字",
            acceptPassword: "请输入密码，有效长度 {0} 到 {1}",
            acceptNumber: "请输入数值，有效范围从 {0} 到 {1}",
            dataInvalid: "{0}无效，请重新输入。",
            dataNotMatch: "数据格式不匹配",
            notSupport: "当前浏览器不支持异步数据传输",
            hide: "隐藏",
            show: "显示",
            refresh: "刷新",
            loadPage: "加载页面",
            loading: "正在处理数据...",
            loadingData: "正在载入数据...",
            loadingSuccess: "载入数据完成",
            loadingFailed: "载入数据失败！",
            // Data text
            executing: "{0}",
            operation: "执行操作",
            executeSending: "发送请求到服务器...",
            updateSuccess: "更新数据成功",
            updateFailed: "更新数据失败！",
            dataNotExist: "请求的数据不存在",
            confirm: "{0}吗？",
            confirmFailed: "{0}失败，是否继续执行？",
            confirmDetail: "剩余 {0} 条数据，执行详情：{1}",
            confirmCancel: "确定要中断当前操作吗？",
            confirmCancelNote: "已执行的请求将不会被取消",
            canceling: "正在等待停止...",
            canceled: "{0}操作被中断",
            success: "{0}成功",
            failedDetail: "{0}未完全成功",
            total: "共 {0} 条数据",
            status1: "{0}成功{2}",
            status2: "{0}失败，方法调用错误。{2}",
            status3: "{0}失败，通信异常。{2}",
            status4: "{0}失败，数据无效。{2}",
            status5: "{0}失败，未登录或登录超时。{2}",
            status6: "{0}失败，无权访问。{2}",
            status7: "{0}失败，数据处理异常。{2}",
            status8: "{0}失败。{2}",
            // Datagrid text
            dataOption: "数据选项",
            searcher: "高级搜索",
            index: "行号",
            choose: "选择",
            totalCount: "总数",
            loaded: "载入",
            status: "状态",
            search: "搜索",
            searchClear: "清空搜索条件",
            none: "无",
            sorting: "正在执行数据排序...",
            sortComplete: "数据排序完成",
            searchRequire: "请输入搜索内容",
            editorInputTips: "使用“上下箭头”或 “CTRL+左右箭头”可移动到其它单元格",
            editorTextTips: "使用“CTRL+上下左右箭头”可移动到其它单元格",
            editorSave: "是否保存正在编辑的数据？",
            needsRow: "请先选择一行",
            oneRowOnly: "请仅选中一行"
        }
    };

    /**
     * 全局配置对象
     */
    this.config = new $Config();
    /**
     * 浏览器信息
     */
    this.navigator = new $Navigator();
    /**
     * 图标类型
     */
    this.icon = new $IconType();
    /**
     * 按钮类型
     */
    this.button = new $ButtonType();
    /**
     * 数据类型
     */
    this.dataType = new $DataType();
    /**
     * 状态代码
     */
    this.status = new $StatusCode();
    /**
     * 网页链接信息
     */
    this.url = new $Url();

    /**
     * 操作按键数组
     */
    var ControlKeys = [8, 9, 13, 16, 17, 18, 20, 27, 33, 34, 35, 36, 37, 38, 40, 144, 45, 46, 91, 93, 39, 92];
    /**
     * Opera 操作按键数组
     */
    var ControlKeysOpera = [8, 9, 13, 16, 17, 18, 20, 27, 33, 34, 35, 36, 37, 38, 40, 144];

    // ------------------------- 字符串处理方法定义 --------------------------

    /**
     * 去除前后空格处理（返回非 null 字符串）
     * @param {String} value 字符串
     * @returns {String} [去除空格后的字符串]
     */
    this.trim = function(value) {
        if (!value) return "";
        var str = "" + value;
        return "" + str.replace(/(^\s*)|(\s*$)/g, "");
    };

    /**
     * 使用 UTF-8 格式编码 URL 参数字符串
     * @param {String} str 字符串
     * @returns {String} 编码结果
     */
    this.encodeUrl = function(str) {
        str += "";
        var spc = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_.*@", ch, r, c, t, i, ret = "", l = str.length;
        for (i = 0; i < l; i++) {
            ch = str.charAt(i);
            if (ch == " ") {
                r = "+";
            } else if (spc.indexOf(ch) != -1) {
                r = ch;
            } else {
                c = ch.charCodeAt(0);
                if (c < 0x80) {
                    r = "%" + c.toString(16).toUpperCase();
                } else if (c >= 0x80 && c < 0x800) {
                    t = c % 0x40;
                    c -= t;
                    c /= 0x40;
                    r = "%" + (0xC0 + c).toString(16) + "%" + (0x80 + t).toString(16);
                    r = r.toUpperCase();
                } else if (c >= 0x800 && c <= 0xFFFF) {
                    t = c % 0x40;
                    r = "%" + (0x80 + t).toString(16);
                    c -= t;
                    c /= 0x40;
                    t = c % 0x40;
                    r = "%" + (0x80 + t).toString(16) + r;
                    c -= t;
                    c /= 0x40;
                    r = "%" + (0xE0 + c).toString(16) + r;
                    r = r.toUpperCase();
                } else {
                    r = ch;
                }
            }
            ret += r;
        }
        return ret;
    };

    /**
     * 使用 UTF-8 格式解码 URL 参数字符串
     * @param {String} str 字符串
     * @returns {String} 解码结果
     */
    this.decodeUrl = function(str) {
        str += "";
        var ret = "";
        var i = 0, c = 0, l = str.length;
        while (i < (l - 1)) {
            var ch = str.charAt(i);
            i++;
            if (ch == "+") {
                ret += " ";
            } else if (ch == "%" && str.charAt(i) != "%") {
                c = parseInt("0x" + str.substr(i, 2));
                i += 3;
                if (c <= 0x7f) {
                    i--;
                } else if (c >= 0xC0 && c <= 0xDF) {
                    c -= 0xC0;
                    c *= 0x40;
                    c += parseInt("0x" + str.substr(i, 2)) - 0x80;
                    i += 2;
                } else if (c >= 0xE0) {
                    c -= 0xE0;
                    c *= 0x1000;
                    c += (parseInt("0x" + str.substr(i, 2)) - 0x80) * 0x40;
                    i += 3;
                    c += parseInt("0x" + str.substr(i, 2)) - 0x80;
                    i += 2;
                }
                ret += String.fromCharCode(c);
            } else {
                ret += ch;
            }
        }
        if (i < l) {
            ret += unescape(str.substr(i, l - i));
        }
        return ret;
    };

    /**
     * 编码 HTML
     * @param {String} str 字符串
     * @param {Number} bit 编码进制（默认使用 16 进制编码）
     * @returns {String} 编码结果
     */
    this.encodeHtml = function(str, bit) {
        str += "";
        var rs = "";
        var b = (typeof bit == "number") ? bit : 16;
        var s = (b == 16) ? "&#x" : "&#";
        try {
            var i = 0;
            var l = str.length;
            while (i < l) {
                var x = str.charCodeAt(i++).toString(b);
                rs += s + x.toUpperCase() + ";";
            }
        } catch (e) {
            $W.debug($G.getText("dataError"), e.message, e.stack);
        }
        return rs;
    };

    /**
     * 解码 HTML
     * @param {String} str 字符串
     * @param {Number} bit 解码进制（可选，默认使用 16 进制编码）
     * @returns {String} 解码结果
     */
    this.decodeHtml = function(str, bit) {
        str += "";
        var rs = "", s, h;
        var b = (typeof bit == "number") ? bit : 16;
        if (b == 16) {
            s = "&#x";
            h = "0x";
        } else if (b == 8) {
            s = "&#";
            h = "0";
        } else {
            s = "&#";
            h = "";
        }
        var re = new RegExp(s);
        try {
            var i = 0;
            var k = str.split(";");
            var l = k.length;
            while (i < l) {
                var m = k[i++].replace(re, "");
                var x = parseInt(h + m, b);
                rs += String.fromCharCode(x);
            }
        } catch (e) {
            $W.debug($G.getText("dataError"), e.message, e.stack);
        }
        return rs;
    };

    /**
     * 删除 HTML 标签处理
     * @param {String} str 需处理的字符串
     * @returns {String} 字符串
     */
    this.deleteHtmlTag = function(str) {
        if (!str) return str;
        str = str.replace(/(<\/?(br|p)[^>\/]*\/?>)/img, "\n");
        return str.replace(/(<\/?[^>\/]*)\/?>/img, "");
    };

    /**
     * 字符串转换为十六进制处理
     * @param {String} s 字符串
     * @returns {String} 十六进制字符串
     */
    this.stringToHex = function(s) {
        s += "";
        var r = "";
        var hexes = new Array("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f");
        for (var i = 0, c = s.length; i < c; i++) {
            var ch = s.charCodeAt(i);
            r += hexes[ch >> 4] + hexes[ch & 0xf];
        }
        return r.toUpperCase();
    };

    /**
     * 十六进制转换为字符串处理
     * @param {String} s 十六进制字符串
     * @returns {String} 原字符串
     */
    this.hexTostring = function(s) {
        s += "";
        var r = "";
        for (var i = 0, c = s.length; i < c; i += 2) {
            var sxx = parseInt(s.substring(i, i + 2), 16);
            r += String.fromCharCode(sxx);
        }
        return r;
    };

    /**
     * 获取字符串长度（按字节）
     * @param {String} s 字符串
     * @returns {Number} 字符串字节长度
     */
    this.getBytesLength = function(s) {
        s += "";
        var l = 0;
        for (var i = 0, c = s.length; i < c; i++) {
            l += (s.charCodeAt(i) > 255 ? 2 : 1);
        }
        return l;
    };

    /**
     * 根据长度截取字符串，截取完成后最后添加省略号（...），返回非 null 字符串
     * @param {String} str 需要处理的字符串
     * @param {Number} bytesLength 字节长度
     * @returns {String} 处理后的字符串
     */
    this.limitString = function(str, bytesLength) {
        if (!str) return "";
        if (str.length <= bytesLength) return str;
        var l = 0;
        for (var i = 0, c = str.length; i < c; i++) {
            l += (str.charCodeAt(i) > 255 ? 2 : 1);
            if (l > bytesLength) return str.substring(0, i) + "...";
        }
        return str;
    };

    // ------------------------- 字符串格式判断方法 --------------------------

    /**
     * 判断字符串是否日期格式
     * @param {String} str 字符串
     * @returns {Boolean} 是否日期格式
     */
    this.isDateFormat = function(str) {
        if (!str) return false;
        var re = new RegExp("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$");
        return (str.search(re) != -1);
    };

    /**
     * 判断字符串是否时间格式
     * @param {String} str 字符串
     * @returns {Boolean} 是否时间格式
     */
    this.isTimeFormat = function(str) {
        if (!str) return false;
        var re = new RegExp("^((((0?[0-9])|([1][0-9])|([2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        return (str.search(re) != -1);
    };

    /**
     * 判断字符串是否日期及时间格式
     * @param {String} str 字符串
     * @returns {Boolean} 是否日期时间格式
     */
    this.isDateTimeFormat = function(str) {
        if (!str) return false;
        var re = new RegExp("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1][0-9])|([2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        return (str.search(re) != -1);
    };

    /**
     * 判断字符串是否邮件格式
     * @param {String} str 字符串
     * @returns {Boolean} 是否邮件格式
     */
    this.isEmailFormat = function(str) {
        if (!str) return false;
        return (str.search(/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/) != -1);
    };

    /**
     * 判断字符串是否数值格式
     * @param {String} str 字符串
     * @returns {Boolean} 是否数值格式
     */
    this.isNumericFormat = function(str) {
        if (!str) return false;
        return (str.search(/^(-|\+)?\d+(\.\d+)?$/) != -1);
    };

    /**
     * 判断字符串是否无符号数值格式
     * @param {String} str 字符串
     * @returns {Boolean} 是否无符号数值格式
     */
    this.isUNumericFormat = function(str) {
        if (!str) return false;
        return (str.search(/^\d+(\.\d+)?$/) != -1);
    };

    /**
     * 判断字符串是否整数格式
     * @param {String} str 字符串
     * @returns {Boolean} 是否整数格式
     */
    this.isIntegerFormat = function(str) {
        if (!str) return false;
        return (str.search(/^(-|\+)?\d+$/) != -1);
    };

    /**
     * 判断字符串是否无符号整数格式
     * @param {String} str 字符串
     * @returns {Boolean} 是否无符号整数格式
     */
    this.isUIntegerFormat = function(str) {
        if (!str) return false;
        return (str.search(/^\d+$/) != -1);
    };

    /**
     * 判断字符串是否浮点数格式
     * @param {String} str 字符串
     * @returns {Boolean} 是否浮点数格式
     */
    this.isFloatFormat = function(str) {
        if (!str) return false;
        return (str.search(/^(-?\d+)(\.\d+)?$/) != -1);
    };

    /**
     * 判断字符串是否无符号浮点数格式
     * @param {String} str 字符串
     * @returns {Boolean} 是否无符号浮点数格式
     */
    this.isUFloatFormat = function(str) {
        if (!str) return false;
        return (str.search(/^\d+(\.\d+)?$/) != -1);
    };

    /**
     * 判断字符串是否英语格式
     * @param {String} str 字符串
     * @returns {Boolean} 是否英语格式
     */
    this.isEnglishFormat = function(str) {
        if (!str) return false;
        return (str.search(/^[A-Za-z]+$/) != -1);
    };

    /**
     * 判断字符串是否中文格式
     * @param {String} str 字符串
     * @returns {Boolean} 是否中文格式
     */
    this.isChineseForamt = function(str) {
        if (!str) return false;
        return (str.search(/^[\u4e00-\u9fa5]+$/) != -1);
    };

    /**
     * 判断字符串是否存在中文字符格式
     * @param {String} str 字符串
     * @returns {Boolean} 是否存在中文字符格式
     */
    this.isExistChinese = function(str) {
        if (!str) return false;
        return (str.search(/[\u4e00-\u9fa5]/) != -1);
    };

    /**
     * 判断字符串是否 ASCII 字符格式
     * @param {String} str 字符串
     * @returns {Boolean} 是否 ASCII 字符格式
     */
    this.isAsciiFormat = function(str) {
        if (!str) return false;
        return (str.search(/^\w+$/) != -1);
    };

    /**
     * 判断字符串是否超链接格式
     * @param {String} str 字符串
     * @returns {Boolean} 是否超链接格式
     */
    this.isURLFormat = function(str) {
        if (!str) return false;
        return (str.search(/^[a-zA-Z]+:\/\/.*\..*/) != -1);
    };

    // ------------------------- 类型处理方法定义 --------------------------

    /**
     * 判断对象是否是数组类型
     * @param {Object} obj 对象
     * @returns {Boolean} 是否是数组类型
     */
    this.isArray = function(obj) {
        if (!obj) return false;
        if (typeof obj == "object") {
            if (obj.constructor == Array) return true;
            var s = obj.constructor ? obj.constructor.toString() : null;
            if (s && s.length > 0) {
                var pos = s.indexOf("function Array()");
                if (pos >= 0 && pos < 10) return true;
            }
        }
        return false;
    };

    /**
     * 判断对象是否是日期类型
     * @param {Object} obj 对象
     * @returns {Boolean} 是否是日期类型
     */
    this.isDate = function(obj) {
        if (!obj) return false;
        return (typeof obj == "object") && (obj.constructor == Date);
    };

    /**
     * 判断是否是操作按键（非键盘输入键）
     * @param {Number} k 键值（keyCode）
     * @returns {Boolean} 是否是操作按键
     */
    this.isControlKey = function(k) {
        var ks = $G.navigator.opera ? ControlKeysOpera : ControlKeys;
        return ks.indexOf(k) != -1;
    };

    /**
     * 判断是否数字键(0 - 9)
     * @param {Number} k 键值（keyCode）
     * @returns {Boolean} 是否数字键(0 - 9)
     */
    this.isNumberKey = function(k) {
        return $G.navigator.opera ? (k > 47 && k < 58) : ((k > 47 && k < 58) || (k > 95 && k < 106));
    };

    /**
     * 判断是否是英文字母键
     * @param {Number} k 键值（keyCode）
     * @returns {Boolean} 是否是英文字母键
     */
    this.isEnglishKey = function(k) {
        return (k > 64 && k < 91);
    };

    /**
     * 判断是否为小数点键
     * @param {Number} k 键值（keyCode）
     * @returns {Boolean} 是否为小数点键
     */
    this.isDotKey = function(k) {
        return $G.navigator.opera ? (k == 46 || k == 78) : (k == 190 || k == 110);
    };

    /**
     * 判断是否负号键（-）
     * @param {Number} k 键值（keyCode）
     * @returns {Boolean} 是否负号键（-）
     */
    this.isNegativeKey = function(k) {
        if ($G.navigator.opera) {
            return k == 45;
        } else if ($G.navigator.ie) {
            return (k == 189 || k == 109);
        } else {
            return k == 109;
        }
    };

    /**
     * 克隆对象
     * @param {Object} obj 对象
     * @returns {Object} 被克隆的对象
     */
    this.cloneObject = function(obj) {
        if (!obj && obj != 0) return null;
        var json = new $JSON();
        try {
            return json.fromJSONString(json.toJSONString(obj));
        } catch (ex) {
            return obj;
        }
    };

    /**
     * 设置数组内所有对象的属性
     * @param {Array} ary 数组
     * @param {String} name 属性名称
     * @param {Object} value 属性值
     * @returns void
     */
    this.setObjectsProperty = function(ary, name, value) {
        if (!ary) return;
        for (var i = 0, c = ary.length; i < c; i++) {
            var data = ary[i];
            if (data) {
                data[name] = value;
            }
        }
    };

    /**
     * 设置对象属性处理
     * @param {Object} obj 对象
     * @param {String} key 属性名称
     * @param {Object} value 属性值
     */
    this.setProperty = function(obj, key, value) {
        if (obj) obj[key] = value;
    };

    /**
     * 比较两个对象内部值是否相同
     * @param {Object} obj1 对象1
     * @param {Object} obj2 对象2
     * @returns {Boolean} 是否相同
     */
    this.compare = function(obj1, obj2) {
        if (obj1 == obj2) return true;
        var t1 = typeof obj1;
        var t2 = typeof obj2;
        if (t1 != t2) return false;
        if (obj1 && !obj2) return false;
        if (!obj1 && obj2) return false;
        if (!obj1 && !obj2) return true;
        // (number,string,boolean,object,function,undefined)
        switch (t1) {
            case "object":
                // 判断数据长度
                var len1 = 0;
                var len2 = 0;
                var p = null;
                for (p in obj1) {
                    len1++;
                }
                for (p in obj2) {
                    len2++;
                }
                if (len1 != len2) return false;
                if (obj1.constructor == obj2.constructor) {
                    for (p in obj1) {
                        if (!$G.compare(obj1[p], obj2[p])) return false;
                    }
                    return true;
                }
                return false;
            case "function":
                return obj1.toString() == obj2.toString();
            default:
                return obj1 == obj2;
        }
    };

    /**
     * 修正字符串为正则表达式对应的字符
     * @param {String} str 需要修正的字符串
     * @param {Array} excludes 不包含的关键字
     * @returns {String} 修正的字符串
     */
    this.fixToRegExpString = function(str, excludes) {
        if (!str) return "";
        if (typeof excludes == "string") {
            excludes = [excludes];
        }
        return str.replace(/([\$\(\)\[\]\{\}\*\+\-\?\\\^\|\.\\])/mg, function($0) {
            return (excludes && excludes.indexOf($0) >= 0) ? $0 : ("\\" + $0);
        });
    };

    /**
     * 获取对象属性值在对象中的位置
     * @param {Object} obj 对象
     * @param {Object} value 属性值
     * @returns {Number} 位置（-1 表示未找到）
     */
    this.indexOfObject = function(obj, value) {
        if (!obj) return -1;
        var i = -1;
        for ( var p in obj) {
            i++;
            if (obj[p] == value) { return i; }
        }
        return -1;
    };

    /**
     * 获取数组值在数组中的位置
     * @param {Array} ary 对象
     * @param {Object} value 数组值
     * @returns {Number} 位置（-1 表示未找到）
     */
    this.indexOfArray = function(ary, value) {
        if (!ary) return -1;
        for (var i = 0, c = ary.length; i < c; i++) {
            if (ary[i] == value) { return i; }
        }
        return -1;
    };

    /**
     * 把参数对象转换为数组，需要使用apply方法调用（返回非 null 数组）
     * @param {Object} thisObj 需使用的 this 对象
     * @param {Object} args 传入的参数队列
     * @returns {Array} 数组对象
     */
    this.argumentsToArray = function(thisObj, args) {
        if (!arguments) return [];
        var ret = [];
        for (var i = 0, c = arguments.length; i < c; i++) {
            ret.push(arguments[i]);
        }
        return ret;
    };

    /**
     * 数据排序处理（比较两个数据大小，返回排序顺序）<br/> 负值，如果所传递的第一个参数比第二个参数小<br/> 零，如果两个参数相等<br/> 正值，如果第一个参数比第二个参数大<br/>
     * @param {Object} v1 第一个数值
     * @param {Object} v2 第二个数值
     * @returns {Number} 比较结果：-1/0/1
     */
    this.compareSort = function(v1, v2) {
        if (typeof v1 == "number" && typeof v2 == "number") return v1 == v2 ? 0 : (v1 < v2 ? -1 : 1);
        if (!v1) {
            return (!v2 ? 0 : -1);
        } else if (!v2) {
            return (!v1 ? 0 : 1);
        } else {
            if (!v1) {
                v1 = "";
            } else {
                v1 += "";
            }
            if (!v2) {
                v2 = "";
            } else {
                v2 += "";
            }
            if (v1 == v2) return 0;
            v1 = v1.replace(/(\d+)/mg, function(n1) {
                return $G.duplicate("0", 10 - n1.length) + n1;
            });
            v2 = v2.replace(/(\d+)/mg, function(n2) {
                return $G.duplicate("0", 10 - n2.length) + n2;
            });
            return v1.localeCompare(v2);
        }
    };

    /**
     * 生成多个重复的字符串
     * @param {String} str 需要重复的字符串
     * @param {Number} count 数量
     * @param {String} seperator 分隔符（默认空字符串：""）
     * @returns {String} 字符串
     */
    this.duplicate = function(str, count, seperator) {
        if (!seperator) seperator = "";
        var strs = [];
        for (var i = 0; i < count; i++) {
            strs[i] = str;
        }
        return strs.join(seperator).toString();
    };

    /**
     * 去除非数字的字符（不会去除小数点及负号）
     * @param {String} str 需处理的字符串
     * @returns {String} 处理结果字符串
     */
    this.stripNonNumeric = function(str) {
        str += "";
        var rgx = /^d|.|-$/;
        var out = "";
        for (var i = 0; i < str.length; i++) {
            if (rgx.test(str.charAt(i))) {
                if (!((str.charAt(i) == "." && out.indexOf(".") != -1) || (str.charAt(i) == "-" && out.length != 0))) {
                    out += str.charAt(i);
                }
            }
        }
        return out;
    };

    /**
     * 格式化数据方法（返回非 null 字符串）
     * @param {Object} value 需要格式化的数据
     * @param {String} type 数据类型（可选，对应：$G.dataType.XXXX）
     * @param {String} format 格式化字符串（可选，对应数据类型，如日期类型："yyyy-mm-dd hh:MM:ss.zzz"；数值类型："0,0.00"；字符串类型："a{0}"；数组类型：","）
     * @returns {String} 格式化结果
     */
    this.formatValue = function(value, type, format) {
        var text = "";
        if ((value || value == 0) && type && type.length > 0) {
            switch (type) {
                case $G.dataType.STRING:
                    if (format && format.length > 0) {
                        text = $G.formatString(format, value);
                    } else {
                        text += value;
                    }
                    break;
                case $G.dataType.NUMBER:
                case $G.dataType.INT:
                case $G.dataType.FLOAT:
                    if (format && format.length > 0) {
                        text = $G.formatNumber(value, format);
                    } else {
                        text += value;
                    }
                    break;
                case $G.dataType.DATE:
                    text = $G.formatDate($G.toDate(value), format);
                    break;
                case $G.dataType.PASSWORD:
                    text = $G.duplicate("*", (value + "").length);
                    break;
                default:
                    text += value;
                    break;
            }
        } else {
            if (value) text += value;
        }
        return $G.trim(text);
    };

    /**
     * 格式化字符串<br/> 如果将 arguments 数组传递给此方法，则数组中包含的参数会转换成字符串，然后在返回前在字符串中按顺序替代占位符 "{0}"、"{1}" 等。
     * @param {String} str 字符串，可包含：{0}..{n} 占位符
     * @param {Object} parameters 需要替换{0}..{n}的参数列表或数组
     * @returns {String} 格式化后的字符串
     */
    this.formatString = function(str, parameters) {
        if (!str || str.length == 0) return "";
        var json = new $JSON();
        if (typeof str == "object") {
            try {
                str = json.toJSONString(str);
            } catch (ex) {
                str = "" + str;
            }
        } else {
            str = "" + str;
        }
        var args, i, n = 0;
        if ($G.isArray(parameters)) {
            args = parameters;
            i = 0;
        } else {
            args = arguments;
            i = 1;
        }
        var c = args.length;
        var ret = str;
        for (; i < c; i++) {
            var s = args[i];
            if (s || s == 0) {
                if (typeof s == "object") {
                    try {
                        s = json.toJSONString(s);
                    } catch (ex) {
                        s = "" + s;
                    }
                }
            } else {
                s = "";
            }
            var param = "" + s;
            ret = ret.replace(new RegExp("\\{" + n + "\\}", "img"), param);
            n++;
        }
        return "" + ret;
    };

    /**
     * 格式化数值方法
     * @param {Number} value 需格式化的数值
     * @param {String} format 格式化字符串（可选，默认："0.00"；格式如：0,0.00）
     * @returns {String} 格式字符串
     */
    this.formatNumber = function(value, format) {
        value = $G.toNumber(value, 0);
        if (!format || format.length == 0) format = "0.00";
        var hasComma = -1 < format.indexOf(","), psplit = $G.stripNonNumeric(format).split(".");
        if (1 < psplit.length) {
            value = value.toFixed(psplit[1].length);
        } else {
            value = value.toFixed(0);
        }
        var fnum = value.toString();
        if (hasComma) {
            psplit = fnum.split(".");
            var cnum = psplit[0], parr = [], j = cnum.length, m = Math.floor(j / 3), n = cnum.length % 3 || 3;
            for (var i = 0; i < j; i += n) {
                if (i != 0) n = 3;
                parr[parr.length] = cnum.substr(i, n);
                m -= 1;
            }
            fnum = parr.join(",");
            if (psplit[1]) fnum += "." + psplit[1];
        }
        return format.replace(/[d,?.?]+/, fnum);
    };

    /**
     * 格式化日期处理
     * @param {Date} date 日期对象
     * @param {String} style 格式化字符串（可选，如：yyyy-mm-dd hh:MM:ss.zzz(fff)，默认：yyyy-mm-dd hh:MM:ss）
     * @returns {String} 字符串
     */
    this.formatDate = function(date, style) {
        if (!date) return "";
        var s = (style && style.length > 0) ? style : "yyyy-mm-dd hh:MM:ss";
        var h2, y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        var h = date.getHours();
        var mi = date.getMinutes();
        var se = date.getSeconds();
        var ms = date.getMilliseconds();
        if (s.indexOf("AMPM") != -1) {
            s = s.replace(/(AMPM)/g, "NN");
            h2 = (h > 12) ? (h - 12) : h;
            h2 = (h2 == 0) ? 12 : h2;
        } else {
            h2 = h;
        }
        var res = [/(z+|f+)/, /(y+)/, /(m{1,2})/, /(d{1,2})/, /(h{1,2})/, /(M{1,2})/, /(s{1,2})/];
        var aes = ["" + ms, "" + y, "" + m, "" + d, "" + h2, "" + mi, "" + se];
        var c = res.length;
        for (var n = 0; n < c; n++) {
            var re = res[n];
            var as = aes[n];
            var l = 0;
            if (n > 0) {
                l = as.length;
            }
            while (re.test(s)) {
                var zs = "";
                var ml = 0;
                var rl = RegExp.$1.length;
                if (n == 0) {
                    as = "" + Math.floor(as / Math.pow(10, 3 - rl));
                    l = as.length;
                }
                if (rl > l) {
                    ml = l;
                    for (var i = l; i < rl; i++) {
                        zs += "0";
                    }
                } else {
                    ml = rl;
                }
                s = s.replace(re, zs + ((n < 2) ? as.substr(l - ml) : as));
            }
        }
        s = s.replace(/(N{2})/g, (h < 12) ? "AM" : "PM");
        return s;
    };

    /**
     * 把数据转换为数值
     * @param {Object} value 数据对象
     * @param {Number} defaultValue 默认值
     * @returns {Number} 数值
     */
    this.toNumber = function(value, defaultValue) {
        var v = 0;
        try {
            v = +value;
            if (isNaN(v)) v = (defaultValue ? defaultValue : 0);
        } catch (e) {
            v = (defaultValue ? defaultValue : 0);
        }
        return v;
    };

    /**
     * 把数据转换为整数
     * @param {Object} value 数据对象
     * @param {Number} defaultValue 默认值
     * @returns {Number} 整数
     */
    this.toInt = function(value, defaultValue) {
        if (typeof value == "number") return Math.round(value);
        var v = parseInt(value);
        return isNaN(v) ? (defaultValue ? defaultValue : 0) : v;
    };

    /**
     * 把数据转换为日期
     * @param {Date} value 日期数据对象（字符串，数值或其他日期格式）
     * @param {Date} defaultValue 默认日期
     * @returns {Date} 日期对象
     */
    this.toDate = function(value, defaultValue) {
        if (!defaultValue) defaultValue = null;
        if (!value) return defaultValue;
        if ($G.isDate(value)) return value;
        if (typeof value == "number") return new Date(value);
        value += "";
        var dt = defaultValue;
        var re, y, m, d, h, mi, ss;
        re = new RegExp(/^(\d{1,4})[\-\/\s]?(\d{1,2})[\-\/\s]?(\d{1,2})[\s|T]?(\d{1,2})[:]?(\d{1,2})[:]?(\d{1,2})$/);
        if (re.exec(value) != null) {
            try {
                y = RegExp.$1;
                m = RegExp.$2;
                d = RegExp.$3;
                h = RegExp.$4;
                mi = RegExp.$5;
                ss = RegExp.$6;
                dt = new Date($G.toInt(y), $G.toInt(m) - 1, $G.toInt(d), $G.toInt(h), $G.toInt(mi), $G.toInt(ss));
            } catch (e) {
            }
        }
        return dt;
    };

    /**
     * 把数据转换为布尔型
     * @param {Object} value 数据对象
     * @param {Boolean} defaultValue 默认返回值
     * @returns {Boolean} 布尔数据
     */
    this.toBoolean = function(value, defaultValue) {
        if (typeof value == "undefined") {
            return defaultValue;
        } else if (typeof value == "string") {
            value = value.toUpperCase();
            return (value == "TRUE" || value == "Y");
        }
        return !!value;
    };

    /**
     * 获取随机数字
     * @param {Number} maxLength 数字长度
     * @returns {String} 字符串
     */
    this.randomNumbers = function(maxLength) {
        var ret = "";
        for (var i = 0; i < maxLength; i++) {
            ret += parseInt(Math.random(i) * 10);
        }
        return ret;
    };

    /**
     * 获取随机ASCII文字（含数字）
     * @param {Number} maxLength 随机文字长度
     * @returns {String} 字符串
     */
    this.randomWords = function(maxLength) {
        var ws = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        var ret = "";
        for (var i = 0; i < maxLength; i++) {
            ret += ws.charAt(Math.floor(Math.random() * 62));
        }
        return ret;
    };

    // ------------------------- 国际化方法定义 --------------------------

    /**
     * 设置多国语言类型
     * @param {String} langKey 语言标识（如：zh_CN, en_US...）
     * @returns void
     */
    this.setLanguageKey = function(langKey) {
        $G.config["langKey"] = langKey;
        var cookies = new $Cookie();
        cookies.set("AXLANGKEY", langKey);
    };

    /**
     * 获取多国语言标识（如：zh_CN, en_US...）
     * @returns {String} 语言标识
     */
    this.getLanguageKey = function() {
        var key = $G.config["langKey"];
        if (key) return key;
        var cookies = new $Cookie();
        key = cookies.get("AXLANGKEY");
        if (!key || key.length == 0) key = $G.config["localKey"];
        $G.setLanguageKey(key);
        return key;
    };

    /**
     * 获取全局语言文字（返回非 null 字符串）
     * @param {String} id 语言文字标识
     * @param {Object} parameters 需要替换{0}..{n}的参数队列
     * @returns {String} 文字
     */
    this.getText = function(id, parameters) {
        var str = $G.globalText[$G.getLanguageKey()][id];
        if (arguments && arguments.length > 1) {
            var ary = $G.argumentsToArray.apply(this, arguments);
            ary.shift();
            str = $G.formatString(str, ary);
        }
        return str ? str : "";
    };

    // ------------------------- 导入文件方法定义 --------------------------

    /**
     * 改变样式模板目录
     * @param {String} folderName 样式库目录名称
     * @returns void
     */
    this.changeThemeFolder = function(folderName) {
        if (!folderName) return;
        var path = $G.config["themesFolder"];
        var links = document.getElementsByTagName("LINK");
        if (!links) return;
        for (var i = 0, c = links.length; i < c; i++) {
            var link = links[i];
            if ($G.trim(link.type).toLowerCase() == "text/css") {
                var href = $G.url.getRelativeUrl(link.href);
                var pos = href.toLowerCase().indexOf(path.toLowerCase());
                if (pos == 0) {
                    var cssurl = href.substr(path.length);
                    if (cssurl.startsWith("/")) cssurl = cssurl.substr(1);
                    var namepos = cssurl.indexOf("/");
                    if (namepos != -1) {
                        var cname = cssurl.substr(0, namepos);
                        if (cname == folderName) continue;
                        link.href = path + folderName + cssurl.substr(namepos);
                    }
                }
            }
        }
    };

}

// ----------------------------- 类型对象定义 ------------------------------

/**
 * 浏览器信息
 */
function $Navigator() {
    /**
     * IE 浏览器
     */
    this.ie = null;
    /**
     * FIREFOX 浏览器
     */
    this.firefox = null;
    /**
     * CHROME 浏览器
     */
    this.chrome = null;
    /**
     * OPERA 浏览器
     */
    this.opera = null;
    /**
     * SAFARI 浏览器
     */
    this.safari = null;
}

/**
 * 数据类型
 */
function $DataType() {
    /**
     * 字符串类型
     */
    this.STRING = "string";
    /**
     * 密码字符串类型
     */
    this.PASSWORD = "password";
    /**
     * 数值类型
     */
    this.NUMBER = "number";
    /**
     * 整数类型
     */
    this.INT = "int";
    /**
     * 浮点数类型
     */
    this.FLOAT = "float";
    /**
     * 布尔类型
     */
    this.BOOLEAN = "boolean";
    /**
     * 日期类型
     */
    this.DATE = "date";
}

/**
 * 图标类型
 */
function $IconType() {
    /**
     * 信息图标
     */
    this.info = "icon_info";
    /**
     * 警告图标
     */
    this.alarm = "icon_alarm";
    /**
     * 问题图标
     */
    this.question = "icon_question";
    /**
     * 加载动画图标
     */
    this.loading = "icon_loading";
    /**
     * 提示信息普通图标
     */
    this.tipInfo = "icon_tip_info";
    /**
     * 提示信息完成图标
     */
    this.tipOk = "icon_tip_ok";
    /**
     * 提示信息错误图标
     */
    this.tipError = "icon_tip_error";
    /**
     * 提示信息警告图标
     */
    this.tipAlarm = "icon_tip_alarm";
}

/**
 * 按钮类型
 */
function $ButtonType() {
    /**
     * “确定”按钮
     */
    this.ok = 1;
    /**
     * “是”按钮
     */
    this.yes = 2;
    /**
     * “全部是”按钮
     */
    this.yesToAll = 4;
    /**
     * “否”按钮
     */
    this.no = 8;
    /**
     * “全部否”按钮
     */
    this.noToAll = 16;
    /**
     * “取消”按钮
     */
    this.cancel = 32;
    /**
     * 标题栏关闭按钮
     */
    this.titleClose = 64;
    /**
     * 标题栏最大化按钮
     */
    this.titleMaximize = 128;
    /**
     * 标题栏不显示按钮
     */
    this.titleNone = 256;
}

// ======================================= TODO: 窗口对象处理对象 ====================================

/**
 * 窗口元素操作类
 */
function $Window() {

    /**
     * 当前最大层次值
     */
    var _maxZIndex = 10;
    /**
     * 窗口是否加载完毕标识
     */
    var _windowLoaded = false;
    /**
     * 窗口加载完毕后需要处理的函数队列
     */
    var _onloadSequence = [];
    /**
     * 辅助按键是否已经按下标识
     */
    var _isOptionKeyDown = false;

    // -------------------------- 加载方法定义 -----------------------------

    /**
     * 加载完成处理（内部方法，禁止调用）
     */
    this.onWindowReady = function() {
        if (_windowLoaded) return;
        _windowLoaded = true;
        while (_onloadSequence.length > 0) {
            _onloadSequence.shift().call();
        }
    };

    /**
     * 判断窗口是否已经加载完毕
     * @returns {Boolean} 是否已经加载完毕
     */
    this.windowLoaded = function() {
        return _windowLoaded;
    };

    /**
     * 增加窗口加载完毕后回调方法
     * @param {Function} callback 需要回调的方法，不带参数
     * @returns void
     */
    this.onload = function(callback) {
        if (!callback) return;
        if (_windowLoaded) {
            callback();
        } else {
            _onloadSequence.push(callback);
        }
    };

    /**
     * 设置辅助按键已按下状态（包括：shiftKey/altKey/ctrlKey）
     * @param {Boolean} isDown 是否已经按下
     */
    this.setOptionKeyDown = function(isDown) {
        if (isDown) {
            _isOptionKeyDown = true;
            $W.addClassName(document.body, "noSelection");
        } else if (_isOptionKeyDown) {
            _isOptionKeyDown = false;
            $W.removeClassName(document.body, "noSelection");
        }
    };

    /**
     * 判断辅助按键是否已经被按下（包括：shiftKey/altKey/ctrlKey）
     * @returns {Boolean} 是否已经按下
     */
    this.isOptionKeyDown = function() {
        return _isOptionKeyDown;
    };

    // -------------------------- 事件方法定义 -----------------------------

    /**
     * 绑定处理方法（支持多个方法名称，每个方法名称使用逗号分隔）
     * @param {HTMLElement} e 元素对象
     * @param {String} methodName 方法名称（如：onclick）
     * @param {Function} handel 方法引用（传入：evt - 事件对象）
     * @returns {Boolean} 是否成功
     */
    this.attachEvent = function(e, methodName, handel) {
        if (!e || !methodName || !handel) return false;
        e = $W.id(e);
        if (!e) return false;
        var ms = methodName.split(",");
        var l = ms.length;
        for (var i = 0; i < l; i++) {
            var m = $G.trim(ms[i]);
            if (!m || m.length == 0) continue;
            var ml = m.toLowerCase();
            if (!$G.navigator.opera && e.addEventListener) {
                if (ml.indexOf("on") == 0) m = m.substr(2);
                e.addEventListener(m, handel, false);
            } else if (e.attachEvent) {
                if (ml.indexOf("on") != 0) ml = "on" + ml;
                e.attachEvent(ml, handel);
            }
        }
        return true;
    };

    /**
     * 解除绑定处理方法（支持多个方法名称，每个方法名称使用逗号分隔）
     * @param {HTMLElement} e 元素对象
     * @param {String} methodName 方法名称（如：onclick）
     * @param {Function} handel 方法引用（传入：evt - 事件对象）
     * @returns {Boolean} 是否成功
     */
    this.detachEvent = function(e, methodName, handel) {
        if (!e || !methodName || !handel) return false;
        e = $W.id(e);
        if (!e) return false;
        var ms = methodName.split(",");
        var l = ms.length;
        for (var i = 0; i < l; i++) {
            var m = $G.trim(ms[i]);
            if (!m || m.length == 0) continue;
            var ml = m.toLowerCase();
            if (!$G.navigator.opera && e.removeEventListener) {
                if (ml.indexOf("on") == 0) m = m.substr(2);
                e.removeEventListener(m, handel, false);
            } else if (e.detachEvent) {
                if (ml.indexOf("on") != 0) ml = "on" + ml;
                e.detachEvent(ml, handel);
            }
        }
        return true;
    };

    /**
     * 触发方法处理（支持多个方法名称，每个方法名称使用逗号分隔）
     * @param {HTMLElement} e 元素对象
     * @param {String} methodName 方法名称（如：onclick）
     * @param {Event} evt 需要传递的事件（可选，默认新创建事件）
     * @returns {Boolean} 是否成功
     */
    this.fireEvent = function(e, methodName, evt) {
        if (!e || !methodName) return false;
        e = $W.id(e);
        if (!e) return false;
        var ms = methodName.split(",");
        var l = ms.length;
        for (var i = 0; i < l; i++) {
            var m = $G.trim(ms[i]);
            if (!m || m.length == 0) continue;
            var ml = m.toLowerCase();
            if (e.fireEvent) {
                if (ml.indexOf("on") != 0) ml = "on" + ml;
                if (!evt) evt = document.createEventObject();
                e.fireEvent(ml, evt);
            } else if (document.createEvent) {
                if (ml.indexOf("on") == 0) m = m.substr(2);
                var en;
                if (ml.indexOf("click") >= 0 || ml.indexOf("mouse") >= 0) {
                    en = "MouseEvents";
                } else {
                    en = "HTMLEvents";
                }
                try {
                    if (!evt) {
                        var evt1 = document.createEvent(en);
                        evt1.initEvent(m, true, true);
                        e.dispatchEvent(evt1);
                    } else {
                        e.dispatchEvent(evt);
                    }
                } catch (ex) {
                }
            }
        }
        return true;
    };

    /**
     * 设置鼠标滑轮滚动处理方法
     * @param {HTMLElement} e 元素对象
     * @param {Function} handel 处理鼠标滚轮事件方法（传入：evt - 事件对象）
     * @returns {Boolean} 是否绑定成功
     */
    this.attachOnMouseWheel = function(e, handel) {
        if ($G.navigator.firefox) return $W.attachEvent(e, "DOMMouseScroll", handel);
        return $W.attachEvent(e, "onmousewheel", handel);
    };

    /**
     * 解除鼠标滑轮滚动处理方法
     * @param {HTMLElement} e 元素对象
     * @param {Function} handel 处理鼠标滚轮事件方法
     * @returns {Boolean} 是否解除绑定成功
     */
    this.detachOnMouseWheel = function(e, handel) {
        if ($G.navigator.firefox) return $W.detachEvent(e, "DOMMouseScroll", handel);
        return $W.detachEvent(e, "onmousewheel", handel);
    };

    /**
     * 获取事件对象
     * @param {Event} evt 事件对象
     * @returns {Event} 事件对象
     */
    this.getEvent = function(evt) {
        return evt ? evt : window.event;
    };

    /**
     * 获取事件目标对象
     * @param {Event} evt 事件对象
     * @returns {HTMLElement} 事件目标对象
     */
    this.getEventTarget = function(evt) {
        var ev = evt ? evt : window.event;
        if (ev) { return ev.target || ev.srcElement; }
        return null;
    };

    /**
     * 获取事件转到的目标对象
     * @param {Event} evt 事件对象
     * @returns {HTMLElement} 事件目标对象
     */
    this.getToTarget = function(evt) {
        var ev = evt ? evt : window.event;
        if (ev) {
            var t = ev.type ? ev.type.toLowerCase() : null;
            if (t == "mouseover") {
                return ev.target || ev.toElement;
            } else if (t == "mouseout") {
                return ev.relatedTarget || ev.toElement;
            } else {
                return ev.target || ev.srcElement;
            }
        }
        return null;
    };

    /**
     * 获取事件来源的目标对象
     * @param {Event} evt 事件对象
     * @returns {HTMLElement} 事件目标对象
     */
    this.getFromTarget = function(evt) {
        var ev = evt ? evt : window.event;
        if (ev) {
            var t = ev.type ? ev.type.toLowerCase() : null;
            if (t == "mouseover") {
                return ev.relatedTarget || ev.fromElement;
            } else if (t == "mouseout") {
                return ev.target || ev.fromElement;
            } else {
                return ev.target || ev.srcElement;
            }
        }
        return null;
    };

    /**
     * 判断是否按下鼠标左键
     * @param {Event} evt 事件对象
     * @returns {Boolean} 是否按下左键
     */
    this.isLeftButton = function(evt) {
        var ev = evt ? evt : window.event;
        if (!ev) return false;
        if ($G.navigator.ie) return ev.button == 0 || ev.button == 1;
        return ev.button == 0;
    };

    /**
     * 判断是否按下鼠标右键
     * @param {Event} evt 事件对象
     * @returns {Boolean} 是否按下右键
     */
    this.isRightButton = function(evt) {
        var ev = evt ? evt : window.event;
        if (!ev) return false;
        return ev.button == 2;
    };

    /**
     * 判断是否按下鼠标中键
     * @param {Event} evt 事件对象
     * @returns {Boolean} 是否按下中键
     */
    this.isMiddleButton = function(evt) {
        var ev = evt ? evt : window.event;
        if (!ev) return false;
        if ($G.navigator.ie) return ev.button == 4;
        return ev.button == 1;
    };

    /**
     * 获取事件鼠标滚轮操作值（返回值：>0 - 向下滚动；<0 - 向上滚动；0 - 不滚动）
     * @param {Event} evt 事件对象
     * @returns {Number} 滚动值
     */
    this.getEventWheel = function(evt) {
        var ev = evt ? evt : window.event;
        if (ev) {
            var detail = parseInt(ev.detail ? ev.detail : (ev.wheelDelta ? (ev.wheelDelta * (-1)) : 0));
            if ($G.navigator.firefox) detail *= 30;
            if (isNaN(detail)) detail = 0;
            return detail;
        }
        return 0;
    };

    /**
     * 获取事件按键代码（keyCode）
     * @param {Event} evt 事件对象
     * @returns {Number} 按键值
     */
    this.getEventKeyCode = function(evt) {
        var ev = evt ? evt : window.event;
        if (ev) {
            var kc = parseInt(ev.keyCode || ev.which);
            if (!kc || isNaN(kc)) kc = 0;
            return kc;
        }
        return 0;
    };

    /**
     * 把事件的 KEYCODE 转换为字符串（无效返回 null）
     * @param {Event} evt 事件对象
     * @param {Boolean} includeFuncKey 是否包含功能按键字符串返回（如：F1, Esc 等；默认：false）
     * @returns {String} 按键字符串
     */
    this.keyCodeToString = function(evt, includeFuncKey) {
        var ev = $W.getEvent(evt);
        var key = $W.getEventKeyCode(ev);
        var shiftKey = ev.shiftKey;
        var ch;
        if (key >= 65 && key <= 90) {
            // A-Z
            ch = String.fromCharCode(key);
            return shiftKey ? ch.toUpperCase() : ch.toLowerCase();
        } else if (key >= 48 && key <= 57) {
            // 0-9
            if (shiftKey) return [")", "!", "@", "#", "$", "%", "^", "&", "*", "("][key - 48];
            return String.fromCharCode(key);
        } else {
            // Num pad
            var ks = ($G.navigator.opera) ? [48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 78, 47, 42, 45, 43] : [96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 110, 111, 106, 109, 107];
            var i = ks.indexOf(key);
            if (i != -1) return ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "/", "*", "-", "+"][i];
            // Special keys
            ks = $G.navigator.ie ? [192, 189, 187, 219, 221, 186, 222, 220, 188, 190, 191] : ($G.navigator.opera ? [96, 45, 61, 91, 93, 59, 39, 92, 44, 46, 47] : [192, 109, 61, 219, 221, 59, 222, 220, 188, 190, 191]);
            i = ks.indexOf(key);
            if (i != -1) {
                ks = shiftKey ? ["~", "_", "+", "{", "}", ":", "\"", "|", "<", ">", "?"] : ["`", "-", "=", "[", "]", ";", "'", "\\", ",", ".", "/"];
                return ks[i];
            }
            if (includeFuncKey) {
                // Function key(e.g. F1, Esc)
                ks = [27, 9, 20, 16, 17, 18, 32, 91, 92, 93, 8, 13, 45, 36, 33, 46, 35, 34, 38, 37, 40, 39, 144, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 145, 19];
                i = ks.indexOf(key);
                if (i != -1) return ["Esc", "Tab", "Caps Lock", "Shift", "Ctrl", "Alt", "Space", "Win", "Win", "Menu", "Back Space", "Enter", "Insert", "Home", "Page Up", "Delete", "End", "Page Down", "Up", "Left", "Down", "Right", "Num Lock", "F1",
                    "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "Scroll Lock", "Pause Break"][i];
            }
        }
        return null;
    };

    /**
     * 取消事件
     * @param {Event} evt 自事件对象
     * @param {Boolean} cancelBubble 是否取消冒泡（可选；默认：true - 取消冒泡）
     * @returns {Boolean} 取消事件操作值
     */
    this.cancelEvent = function(evt, cancelBubble) {
        var ev = evt ? evt : window.event;
        if (!ev) return false;
        try {
            ev.returnValue = false;
            ev.keyCode = 0;
            ev.wheelDelta = 0;
            ev.detail = 0;
        } catch (ex) {
        }
        if (ev.preventDefault) {
            try {
                ev.preventDefault();
            } catch (ex) {
            }
        }
        if (cancelBubble != false) {
            ev.cancelBubble = true;
            if (ev.stopPropagation) {
                try {
                    ev.stopPropagation();
                } catch (ex) {
                }
            }
        }
        return false;
    };

    /**
     * 禁止事件冒泡
     * @param {Event} evt 自事件对象
     * @returns {Boolean} 允许事件操作值
     */
    this.cancelEventBubble = function(evt) {
        var ev = evt ? evt : window.event;
        if (ev) {
            if (ev.stopPropagation) {
                ev.stopPropagation();
            }
            ev.cancelBubble = true;
        }
        return true;
    };

    /**
     * 取消滚动冒泡
     * @param {Event} evt 自事件对象
     * @param {HTMLElement} scrollElement 滚动元素对象（可选，不设置直接使用事件目标对象）
     * @returns {Boolean} 允许事件操作值
     */
    this.cancelWheelBubble = function(evt, scrollElement) {
        var wheel = $W.getEventWheel(evt);
        if (wheel == 0) return;
        var e = scrollElement;
        if (!e) e = $W.getEventTarget(evt);
        var top = e.scrollTop + wheel;
        if (top < 0) top = 0;
        if (top > e.scrollHeight) top = e.scrollHeight;
        if (e.scrollTop != top) {
            window.setTimeout(function() {
                e.scrollTop = top;
            }, 10);
        }
        return $W.cancelEvent(evt);
    };

    /**
     * 设置选择文本模式（禁止选择或允许选择）
     * @param {HTMLElement} e 元素
     * @param {Boolean} selection 是否允许选择文本
     * @returns void
     */
    this.enableSelection = function(e, selection) {
        $W.onload(function() {
            if (selection) {
                $W.removeClassName(e, "noSelection");
                $W.detachEvent(e, "onselectstart", $W.cancelEvent);
            } else {
                $W.addClassName(e, "noSelection");
                $W.attachEvent(e, "onselectstart", $W.cancelEvent);
            }
        });
    };

    // -------------------------- 元素辅助方法 -----------------------------

    /**
     * 获取对象方法
     * @param {String} eid 元素标识
     * @returns {HTMLElement} 元素对象
     */
    this.id = function(eid) {
        if (!eid) return null;
        return (typeof eid == "string") ? document.getElementById(eid) : eid;
    };

    /**
     * 获取当前的最大层次值
     * @returns {Number} 最大层次值
     */
    this.getCurrentZIndex = function() {
        return _maxZIndex;
    };

    /**
     * 获取下一个最大层次值
     * @returns {Number} 最大层次值
     */
    this.getNextZIndex = function() {
        if (_maxZIndex < 4294967290) return ++_maxZIndex;
        _maxZIndex = 10;
        return _maxZIndex;
    };

    /**
     * 判断当前对象是否是窗体对象
     * @param {Object} obj 测试的对象
     * @returns {Boolean} 是否是窗体对象
     */
    this.isWindow = function(obj) {
        if (!obj) return false;
        var reg = /Window|global/;
        var inc = reg.test({}.toString.call(obj)) ? true : false;
        var iec = (obj == obj.document && obj.document != obj);
        return (inc || iec);
    };

    /**
     * 判断当前对象是否是文档对象
     * @param {Object} obj 测试的对象
     * @returns {Boolean} 是否是窗体对象
     */
    this.isDocument = function(obj) {
        if (!obj) return false;
        return (obj.createElement && obj.documentElement) ? true : false;
    };

    /**
     * 判断是否是指定的标签
     * @param {HTMLElement} e 元素对象
     * @param {String} tagName 标签名称
     * @returns {Boolean} 是否是指定的标签
     */
    this.isTag = function(e, tagName) {
        if (!e || !tagName) return false;
        if (e && e.tagName && e.tagName.toUpperCase() == tagName.toUpperCase()) return true;
        return false;
    };

    /**
     * 判断是否是可编辑类标签（接受输入）
     * @param {HTMLElement} e 元素对象
     * @returns {Boolean} 是否是可编辑类标签
     */
    this.isEdit = function(e) {
        if (!e || !e.tagName) return false;
        var tag = e.tagName.toUpperCase();
        var ary = ["INPUT", "TEXTAREA", "SELECT", "OPTION"];
        return (ary.indexOf(tag) != -1);
    };

    /**
     * 获取当前窗口选中的文本（返回 null 表示无选中文本）
     * @returns {String} 已选中的文本
     */
    this.getSelectedText = function() {
        var str = "";
        if (window.getSelection) {
            str = "" + window.getSelection();
        } else if (document.selection && document.selection.createRange) {
            str = document.selection.createRange().text;
        }
        return (str && str.length > 0) ? str : null;
    };

    /**
     * 清除窗口选中的文本
     * @returns void
     */
    this.clearSelectedText = function() {
        try {
            if (window.getSelection) {
                window.getSelection().removeAllRanges();
            } else if (document.selection) {
                document.selection.empty();
            }
        } catch (ex) {
        }
    };

    /**
     * 设置元素大小（可修正边框影响问题）
     * @param {HTMLElement} e 元素对象
     * @param {Number} width 宽度（可选，设置为 null 或 0 表示不改变，>0 时设置到指定值）
     * @param {Number} height 高度（可选，设置为 null 或 0 表示不改变，>0 时设置到指定值）
     */
    this.setSize = function(e, width, height) {
        if (!e) return;
        if (!e.parentNode || !$W.windowLoaded()) {
            if (width && width > 0) e.style.width = width + "px";
            if (height && height > 0) e.style.height = height + "px";
            return;
        }
        if (width && width > 0) {
            e.style.width = width + "px";
            var cw = e.offsetWidth;
            if (cw > width) {
                while (e.offsetWidth > width) {
                    cw--;
                    if (cw <= 0) break;
                    e.style.width = cw + "px";
                }
            } else if (cw < width) {
                while (e.offsetWidth < width) {
                    cw++;
                    e.style.width = cw + "px";
                }
            }
        }
        if (height && height > 0) {
            e.style.height = height + "px";
            var ch = e.offsetHeight;
            if (ch > height) {
                while (e.offsetHeight > height) {
                    ch--;
                    if (ch <= 0) break;
                    e.style.height = ch + "px";
                }
            } else if (ch < height) {
                while (e.offsetHeight < height) {
                    ch++;
                    e.style.height = ch + "px";
                }
            }
        }
    };

    /**
     * 获取普通元素尺寸，返回：{width:xxx,height:xxx}
     * @param {HTMLElement} e 元素对象
     * @returns {Object} {width:xxx,height:xxx}
     */
    this.getSize = function(e) {
        if (!e) return {
            width: 0,
            height: 0
        };
        return {
            width: e.offsetWidth,
            height: e.offsetHeight
        };
    };

    /**
     * 获取滚动条信息，返回：{width:xxx,height:xxx,left:xxx,top:xxx}
     * @param {HTMLElement} e 元素对象
     * @returns {Object} {width:xxx,height:xxx,left:xxx,top:xxx}
     */
    this.getScroll = function(e) {
        if (!e) return {
            width: 0,
            height: 0,
            left: 0,
            top: 0
        };
        var t = 0, l = 0, w = 0, h = 0;
        if ($W.isWindow(e)) {
            var te;
            var win = e;
            if (win.innerWidth) {
                w = win.innerWidth + (win.scrollMaxX ? win.scrollMaxX : 0);
            }
            if (win.innerHeight) {
                h = win.innerHeight + (win.scrollMaxY ? win.scrollMaxY : 0);
            }
            if ((te = win.document.documentElement) && (typeof te.scrollTop == "number")) {
                t = te.scrollTop;
                l = te.scrollLeft;
                w = Math.max(w, te.scrollWidth);
                h = Math.max(h, te.scrollHeight);
            }
            if ((te = win.document.body)) {
                t = Math.max(t, te.scrollTop);
                l = Math.max(l, te.scrollLeft);
                w = Math.max(te.scrollWidth);
                h = Math.max(te.scrollHeight);
            }
        } else {
            t = e.scrollTop;
            l = e.scrollLeft;
            w = e.scrollWidth;
            h = e.scrollHeight;
        }
        return {
            width: Math.max(0, w),
            height: Math.max(0, h),
            left: Math.max(0, l),
            top: Math.max(0, t)
        };
    };

    /**
     * 滚动到元素顶部可见位置
     * @param {HTMLElement} e 指定元素
     * @returns void
     */
    this.scrollTopTo = function(e) {
        if (!e) return;
        var pos = $W.getPosition(e);
        this.setWindowScrollTop(window, pos.top);
    };

    /**
     * 滚动到元素左侧可见位置
     * @param {HTMLElement} e 指定元素
     * @returns void
     */
    this.scrollLeftTo = function(e) {
        if (!e) return;
        var pos = $W.getPosition(e);
        $W.setWindowScrollLeft(window, pos.left);
    };

    /**
     * 设置窗口滚动条距离顶部位置
     * @param {Window} win 窗口对象
     * @param {Number} value 距离顶部位置
     * @returns void
     */
    this.setWindowScrollTop = function(win, value) {
        var te = win.document.documentElement;
        if (value < 0) value = 0;
        var wsize = $W.getWindowSize(win);
        if (te && (typeof te.scrollTop == "number")) {
            value = Math.min(te.scrollHeight - wsize.height, value);
            if (te.scrollTop != value) {
                te.scrollTop = value;
            }
            if (te.scrollTop == value && value != 0) { return; }
        }
        if (te = win.document.body) {
            value = Math.min(te.scrollHeight - wsize.height, value);
            if (te.scrollTop != value) {
                te.scrollTop = value;
            }
        }
    };

    /**
     * 设置窗口滚动条距离左边距位置
     * @param {Window} win 窗口对象
     * @param {Number} value 距离左边距位置
     * @returns void
     */
    this.setWindowScrollLeft = function(win, value) {
        var te = win.document.documentElement;
        if (value < 0) value = 0;
        var wsize = $W.getWindowSize(win);
        if (te && (typeof te.scrollLeft == "number")) {
            value = Math.min(te.scrollWidth - wsize.width, value);
            if (te.scrollLeft != value) {
                te.scrollLeft = value;
            }
            if (te.scrollLeft == value && value != 0) { return; }
        }
        if (te = win.document.body) {
            value = Math.min(te.scrollWidth - wsize.width, value);
            if (te.scrollLeft != value) {
                te.scrollLeft = value;
            }
        }
    };

    /**
     * 获取浏览器窗口大小
     * @param {Window} win 窗口对象
     * @returns {Object} {width:xxx,height:xxx}
     */
    this.getWindowSize = function(win) {
        var w = 0, h = 0;
        var doc = win.document;
        var te = doc.documentElement;
        if (te && (te.clientHeight || te.offsetHeight)) {
            h = Math.min(te.clientHeight, te.offsetHeight);
        }
        if (win.innerHeight) {
            h = h > 0 ? Math.min(h, win.innerHeight) : win.innerHeight;
        }
        if (te && (te.clientWidth || te.offsetWidth)) {
            w = Math.min(te.clientWidth, te.offsetWidth);
        }
        if (win.innerWidth) {
            w = w > 0 ? Math.min(w, win.innerWidth) : win.innerWidth;
        }
        if (w == 0 || h == 0) {
            var scroll = $W.getScroll(win);
            if (w == 0) w = scroll.width;
            if (h == 0) h = scroll.height;
        }
        if ((typeof win.boxDebugger == "object") && win == win.$W.getTopWindow()) {
            h -= $G.config["debugerHeight"];
        }
        return {
            width: Math.max(0, w),
            height: Math.max(0, h)
        };
    };

    /**
     * 获取文档页面大小
     * @param {Window} win 窗口对象
     * @returns {Object} {width:xxx,height:xxx}
     */
    this.getDocumentSize = function(win) {
        var w = 0, h = 0, te;
        var doc = win.document;
        if (win.innerHeight) {
            h = win.innerHeight + (win.scrollMaxY ? win.scrollMaxY : 0);
        }
        if ((te = doc.documentElement) && te.clientHeight) {
            h = Math.max(h, te.clientHeight);
        }
        if ((te = doc.body)) {
            h = Math.max(h, te.clientHeight);
        }
        if (win.innerWidth) {
            w = win.innerWidth + (win.scrollMaxX ? win.scrollMaxX : 0);
        }
        if ((te = doc.documentElement) && te.clientWidth) {
            w = Math.max(w, te.clientWidth);
        }
        if ((te = doc.body)) {
            w = Math.max(w, te.clientWidth);
        }
        var scroll = $W.getScroll(win);
        w = Math.max(w, scroll.width);
        h = Math.max(h, scroll.height);
        return {
            width: Math.max(0, w),
            height: Math.max(0, h)
        };
    };

    /**
     * 获取上级窗口 IFRAME 方法
     * @param {HTMLElement} e 元素对象
     * @returns {HTMLIFrameElement} 窗口框架对象
     */
    this.getParentFrame = function(e) {
        if (!e) return null;
        do {
            if ($W.isDocument(e)) {
                return e.parentWindow.frameElement;
            } else if ($W.isWindow(e)) { return e.frameElement; }
        } while (e = e.parentNode);
        return null;
    };

    /**
     * 获取元素当前样式
     * @param {HTMLElement} e 元素对象
     * @param {String} styleName 样式名称
     * @param {String} defaultStyle 默认返回样式
     * @returns {String} 获取到的样式字符串
     */
    this.getStyle = function(e, styleName, defaultStyle) {
        if (!e || !styleName) return defaultStyle;
        var pos = null;
        if (document.defaultView && (typeof document.defaultView.getComputedStyle == "function")) {
            pos = document.defaultView.getComputedStyle(e, null)[styleName];
        } else {
            pos = e.currentStyle ? e.currentStyle[styleName] : null;
        }
        return (pos && pos.length > 0) ? pos : defaultStyle;
    };

    /**
     * 获取元素在文档内的位置
     * @param {HTMLElement} e 元素对象
     * @param {HTMLElement} limitParent 查找结束的上级对象（可选，默认不限制）
     * @param {Boolean} incParentWindow 是否包含IFRAME上级窗口（可选，默认不包含）
     * @returns {Object} {left:xxx,top:xxx}
     */
    this.getPosition = function(e, limitParent, incParentWindow) {
        var fix = 1;
        var l = 0, t = 0, sl = 0, st = 0;
        e = $W.id(e);
        if (e) {
            var p = e;
            if (incParentWindow) {
                if ($W.isDocument(p) || $W.isWindow(p)) {
                    p = $W.getParentFrame(p);
                    if ($G.navigator.chrome && $W.getStyle(p, "position") == "absolute") {
                        l += fix;
                        t += fix;
                    }
                }
            }
            if (p) {
                do {
                    if (limitParent && limitParent == p) break;
                    sl = (typeof p.scrollLeft == "number") ? p.scrollLeft : 0;
                    st = (typeof p.scrollTop == "number") ? p.scrollTop : 0;
                    l -= sl;
                    t -= st;
                    l += p.offsetLeft;
                    t += p.offsetTop;
                    if (incParentWindow && $W.isTag(p, "BODY")) {
                        p = p.ownerDocument ? p.ownerDocument : p.parentNode;
                        do {
                            if ($W.isDocument(p)) {
                                p = p.parentWindow;
                                if (!$G.navigator.chrome) {
                                    var scroll = $W.getScroll(p);
                                    l -= scroll.left;
                                    t -= scroll.top;
                                }
                                p = p.frameElement;
                                break;
                            }
                        } while (p = p.parentNode);
                        if (!p) break;
                        // Iframe position
                        sl = (typeof p.scrollLeft == "number") ? p.scrollLeft : 0;
                        st = (typeof p.scrollTop == "number") ? p.scrollTop : 0;
                        l -= sl;
                        t -= st;
                        l += p.offsetLeft;
                        t += p.offsetTop;
                        // Check style postion
                        if ($G.navigator.chrome && $W.getStyle(p, "position") == "absolute") {
                            l += fix;
                            t += fix;
                        }
                    }
                } while (p && (p = p.offsetParent));
            }
        }
        return {
            left: l,
            top: t
        };
    };

    /**
     * 获取元素对象属性
     * @param {HTMLElement} e 元素对象
     * @param {String} name 属性名称
     * @returns {Object} 数据值
     */
    this.getAttribute = function(e, name) {
        e = $W.id(e);
        if (!e) return null;
        var att = e[name];
        if (att || att == 0) return att;
        if (!e.attributes) return null;
        att = e.attributes[name];
        return att ? (att.value ? att.value : att.nodeValue) : null;
    };

    /**
     * 查找上级对象（条件判断包含对象本身）
     * @param {Node} e 当前对象
     * @param {String} idValue 上级对象标志值（可选，null 表示不查找）
     * @param {String} nameValue 上级对象名称值（可选，null 表示不查找）
     * @param {String} classNameValue 上级对象样式类值（可选，null 表示不查找）
     * @param {String} tagNameValue 上级对象标签值（可选，null 表示不查找）
     * @param {String} attributeName 上级对象属性名称（可选，null 表示不查找）
     * @param {Object} attributeValue 上级对象属性值（可选，null 表示不查找）
     * @param {Number} levels 向上查询最多级数（可选，达到该次数后不再向上查询，默认 0 表示不限制）
     * @param {HTMLElement} limitParent 查找结束的上级对象（可选，默认不限制）
     * @returns {Node} 上级对象（null 表示不存在）
     */
    this.getParent = function(e, idValue, nameValue, classNameValue, tagNameValue, attributeName, attributeValue, levels, limitParent) {
        if (e && e.parentNode) {
            levels = parseInt(levels);
            if (isNaN(levels)) levels = 0;
            var i = 0, p = e;
            if (tagNameValue) tagNameValue = tagNameValue.toUpperCase();
            do {
                if (levels > 0) {
                    if (i > levels) break;
                    i++;
                }
                if (limitParent && limitParent == p) break;
                if (idValue) {
                    if (p.id != idValue) continue;
                }
                if (nameValue) {
                    if (p.name != nameValue) continue;
                }
                if (classNameValue) {
                    if (p.className != classNameValue) continue;
                }
                if (tagNameValue) {
                    var tn = p.tagName;
                    if (tn) tn = tn.toUpperCase();
                    if (tn != tagNameValue) continue;
                }
                if (attributeName) {
                    var att = $W.getAttribute(p, attributeName);
                    if (attributeValue == true) {
                        if (!att) continue;
                    } else if (attributeValue == false) {
                        if (att) continue;
                    } else {
                        if (att != attributeValue) continue;
                    }
                }
                return p;
            } while (p = p.parentNode);
        }
        return null;
    };

    /**
     * 查找子元素对象数组（条件判断不包含对象本身，返回非 null 数组）
     * @param {HTMLElement} e 当前对象
     * @param {String} idValue 子对象标志值（可选，null 表示不查找）
     * @param {String} nameValue 子对象名称值（可选，null 表示不查找）
     * @param {String} classNameValue 子对象样式类值（可选，null 表示不查找）
     * @param {String} tagNameValue 子对象标签值（可选，null 表示不查找）
     * @param {String} attributeName 子对象属性名称（可选，null 表示不查找）
     * @param {Object} attributeValue 子对象属性值（可选，不设置表示不根据此值刷选，但是会返回存在 attributeName 的对象）
     * @returns {Array} 找到的元素对象数组
     */
    this.getChildren = function(e, idValue, nameValue, classNameValue, tagNameValue, attributeName, attributeValue) {
        var rets = [];
        if (!e) return rets;
        if (tagNameValue) tagNameValue = tagNameValue.toUpperCase();
        var els = (tagNameValue && e.getElementsByTagName) ? e.getElementsByTagName(tagNameValue) : e.getElementsByTagName("*");
        if (!els) return rets;
        for (var i = 0, c = els.length; i < c; i++) {
            var p = els[i];
            if (idValue) {
                if (p.id != idValue) continue;
            }
            if (nameValue) {
                if (p.name != nameValue) continue;
            }
            if (classNameValue) {
                if (p.className != classNameValue) continue;
            }
            if (tagNameValue) {
                var tn = p.tagName;
                if (tn) tn = tn.toUpperCase();
                if (tn != tagNameValue) continue;
            }
            if (attributeName) {
                var att = $W.getAttribute(p, attributeName);
                if (attributeValue === true) {
                    if (!att) continue;
                } else if (attributeValue === false) {
                    if (att) continue;
                } else {
                    if (attributeValue != att) continue;
                }
            }
            rets.push(p);
        }
        return rets;
    };

    /**
     * 获取主窗口
     * @returns {Window} 窗口对象
     */
    this.getOpener = function() {
        return window.opener ? window.opener : window;
    };

    /**
     * 获取 IFRAME 对应的 window 对象
     * @param {String} iframeId IFRAME 标识
     * @returns {Window} 窗口对象
     */
    this.getWindow = function(iframeId) {
        var e = $W.id(iframeId);
        return (e) ? e.contentWindow : null;
    };

    /**
     * 获取当前页面最顶层窗口
     * @returns {Window} 窗口对象
     */
    this.getTopWindow = function() {
        return window.top ? window.top : window;
    };

    /**
     * 设置 IFRAME URL 处理
     * @param {String} iframeId IFRAME 对象标识
     * @param {String} url 连接地址
     * @param {Boolean} forceReload 是否强制重载窗口页面（可选，默认：false）
     * @returns void
     */
    this.setIFrameUrl = function(iframeId, url, forceReload) {
        var iframe = $W.id(iframeId);
        if (iframe && url) {
            url = $G.url.getRelativeUrl(url);
            var fw = iframe.contentWindow;
            var iurl = fw ? (fw.location ? fw.location.href : null) : null;
            if (url != iurl || forceReload) {
                if (fw && fw.location) {
                    // Disable history records
                    if (url == iurl) {
                        fw.location.reload();
                    } else {
                        fw.location.replace(url);
                    }
                } else {
                    iframe.src = url;
                }
            }
        }
    };

    /**
     * 打开弹出窗口
     * @param {String} url 页面地址
     * @param {Number} width 宽度（可选，默认：500）
     * @param {Number} height 高度（可选，默认：400）
     * @param {Boolean} resize 是否可变大小（可选，默认：false）
     * @param {Boolean} scrollbar 是否显示滚动条（可选，默认：false）
     * @param {Boolean} status 是否显示状态栏（可选，默认：false）
     * @param {String} name 窗口名称（可选，默认："_blank"）
     * @returns {Window} 窗口对象
     */
    this.openWin = function(url, width, height, resize, scrollbar, status, name) {
        if (!name) name = "_blank";
        scrollbar = scrollbar ? "yes" : "no";
        status = status ? "yes" : "no";
        resize = resize ? "yes" : "no";
        width = parseInt(width);
        if (!width || isNaN(width)) width = 500;
        height = parseInt(height);
        if (!height || isNaN(height)) height = 400;
        var left = (screen.width - width) / 2;
        var top = (screen.height - height) / 2 - 38;
        var win = window.open(url, name, "width=" + width + ",height=" + height + ",top=" + top + ",left=" + left + ",scrollbars=" + scrollbar + ",resizable=" + resize + ",status=" + status);
        win.focus();
        return win;
    };

    /**
     * 打开满窗口页面
     * @param {String} url 页面地址
     * @param {Boolean} resize 是否可变大小（可选，默认：false）
     * @param {Boolean} scrollbar 是否显示滚动条（可选，默认：false）
     * @param {Boolean} status 是否显示状态栏（可选，默认：false）
     * @param {Boolean} fullscreen 是否全屏显示（（可选，默认：false 不全屏）
     * @param {String} name 窗口名称（可选，默认："_blank"）
     * @returns {Window} 窗口对象
     */
    this.openFull = function(url, resize, scrollbar, status, fullscreen, name) {
        if (!name) name = "_blank";
        scrollbar = scrollbar ? "yes" : "no";
        status = status ? "yes" : "no";
        resize = resize ? "yes" : "no";
        fullscreen = fullscreen ? "yes" : "no";
        var w = screen.availWidth - 12;
        var h = screen.availHeight - 38;
        if ($G.navigator.firefox) w += 8;
        if ($G.navigator.chrome) {
            w += 4;
            h -= 20;
        }
        var win = window.open(url, name, "width=" + w + ",height=" + h + ",fullscreen=" + fullscreen + ",toolbar=no,location=no,directories=no,menubar=no,scrollbars=" + scrollbar + ",resizable=" + resize + ",status=" + status + ",top=0,left=0");
        win.focus();
        return win;
    };

    /**
     * 判断是否包含元素
     * @param {HTMLElement} parent 父节点元素
     * @param {HTMLElement} child 子元素
     * @returns {Boolean} 是否包含子元素
     */
    this.contains = function(parent, child) {
        if (parent == null || child == null) return false;
        parent = $W.id(parent);
        child = $W.id(child);
        if (parent.contains) return parent.contains(child);
        do {
            if (child == parent) return true;
        } while (child = child.parentNode);
        return false;
    };

    /**
     * 从文档结构移除对象
     * @param {HTMLElement} e 需要移除的对象
     * @returns void
     */
    this.remove = function(e) {
        if (!e) return;
        if (e.parentNode) {
            if (e.onWindowResize) {
                $W.detachEvent($W.getTopWindow(), "onresize", e.onWindowResize);
            }
            try {
                e.parentNode.removeChild(e);
                return;
            } catch (ex) {
            }
        }
        if (document.body) {
            try {
                document.body.removeChild(e);
            } catch (ex) {
            }
        }
    };

    /**
     * 移除所有子节点
     * @param {HTMLElement} e 父节点元素
     * @returns {Number} 移除的总数
     */
    this.removeChildNodes = function(e) {
        if (!e) return 0;
        if (typeof e == "string") e = $W.id(e);
        var c = 0;
        while (e.childNodes.length > 0) {
            c++;
            $W.remove(e.childNodes[0]);
        }
        return c;
    };

    /**
     * 增加 CSS 样式类
     * @param {HTMLElement} e 元素
     * @param {String} className 样式名称
     * @returns {String} 总样式
     */
    this.addClassName = function(e, className) {
        if (!e) return null;
        var cn = e.className;
        if (!cn || cn.length == 0) {
            e.className = className;
            return className;
        }
        if (!className || className.length == 0) return cn;
        var ary = cn.split(" ");
        if (ary.indexOf(className) == -1) {
            cn += " " + className;
        }
        e.className = cn;
        return cn;
    };

    /**
     * 移除 CSS 样式类
     * @param {HTMLElement} e 元素
     * @param {String} className 样式名称（支持参数队列）
     * @returns {String} 总样式
     */
    this.removeClassName = function(e, className) {
        if (!e) return null;
        var oldClass = e.className;
        if (!oldClass || oldClass.length == 0) return null;
        if (!className || className.length == 0) return oldClass;
        if (arguments.length == 2 && oldClass.indexOf(className) == -1) return oldClass;
        var classes = oldClass.split(" ");
        var newClass = "";
        for (var i = 0, c = classes.length; i < c; i++) {
            var oneClass = classes[i];
            if (!oneClass || oneClass.length == 0) continue;
            var needSkip = false;
            for (var n = 1, m = arguments.length; n < m; n++) {
                if (oneClass == arguments[n]) {
                    needSkip = true;
                    break;
                }
            }
            if (needSkip) continue;
            if (newClass.length > 0) newClass += " ";
            newClass += oneClass;
        }
        if (newClass == oldClass) return oldClass;
        e.className = newClass;
        return newClass;
    };

    /**
     * 设置元素焦点处理（返回是否设置成功）
     * @param {HTMLElement} e 元素对象
     * @param {Number} time 延时时间（可选，设置为 0 或不设置则立即设置焦点）
     * @returns {Boolean} 是否允许设置焦点
     */
    this.setFocus = function(e, time) {
        if (e && e.focus && !e.disabled) {
            if (e.style && e.style.display == "none" && e.style.visibility == "hidden") return false;
            if (time && time > 0) {
                window.setTimeout(function() {
                    e.focus();
                }, time);
            } else {
                e.focus();
            }
            return true;
        }
        return false;
    };

    // ------------------------------ TODO: 创建 DOM 元素方法 ------------------------------

    /**
     * 创建文本节点方法
     * @param {Object} data 数据对象
     * @param {String} fieldId 数据属性名称
     * @param {String} type 数据类型（可选，对应：$G.dataType.XXXX）
     * @param {String} format 格式化字符串（可选，对应数据类型，如日期类型："yyyy-mm-dd hh:MM:ss.zzz"；数值类型："0,0.00"；字符串类型："a{0}"；数组类型：","）
     * @param {Number} bytesLength 字符字节长度（可选，不设置表示不截取长度，双字节字符每个字符 2 字节）
     * @returns {Text} 文本节点对象
     */
    this.createTextNode = function(data, fieldId, type, format, bytesLength) {
        if (!data || !fieldId) return document.createTextNode("");
        var str = $G.formatValue(data[fieldId], type, format);
        if (bytesLength && bytesLength > 0) str = $G.limitString(str, bytesLength);
        return document.createTextNode(str);
    };

    /**
     * 创建按钮
     * @param {String} name 名称（可选）
     * @param {String} title 标题（可选）
     * @param {String} value 值（可选）
     * @param {String} className 样式类名称（可选）
     * @param {Boolean} disabled 是否无效（可选）
     * @param {Number} width 宽度（可选）
     * @param {Number} height 高度（可选）
     * @returns {HTMLButtonElement} 按钮对象
     */
    this.createButton = function(name, title, value, className, disabled, width, height) {
        var e = null;
        if (name) {
            try {
                e = document.createElement('<input type="button" name="' + name + '"/>');
            } catch (ex) {
            }
            if (!e) {
                e = document.createElement("INPUT");
                e.type = "button";
                e.name = name;
            }
        } else {
            e = document.createElement("INPUT");
            e.type = "button";
        }
        if (title) {
            e.title = title;
            e.alt = title;
        }
        if (disabled) e.disabled = true;
        if (width || width == 0) e.style.width = width + "px";
        if (height || height == 0) e.style.height = height + "px";
        if (className) e.className = className;
        e.value = value ? value : "";
        return e;
    };

    /**
     * 创建多选框
     * @param {String} name 名称（可选）
     * @param {String} title 标题（可选）
     * @param {String} value 值（可选）
     * @param {String} className 样式类名称（可选）
     * @param {Boolean} disabled 是否无效（可选）
     * @param {Boolean} checked 是否选中
     * @returns {HTMLInputElement} 多选框对象
     */
    this.createCheckBox = function(name, title, value, className, disabled, checked) {
        checked = checked ? true : false;
        var e = null;
        if (name) {
            try {
                e = document.createElement('<input type="checkbox" name="' + name + '" ' + (checked ? 'checked="true"' : '') + '/>');
            } catch (ex) {
            }
            if (!e) {
                e = document.createElement("INPUT");
                e.type = "checkbox";
                e.name = name;
                e.checked = checked;
            }
        } else {
            e = document.createElement("INPUT");
            e.type = "checkbox";
            e.checked = checked;
        }
        if (title) {
            e.title = title;
            e.alt = title;
        }
        if (disabled) e.disabled = true;
        if (className) e.className = className;
        e.value = value ? value : "";
        return e;
    };

    /**
     * 创建文本输入框
     * @param {String} name 名称（可选）
     * @param {String} title 标题（可选）
     * @param {String} value 值（可选）
     * @param {String} className 样式类名称（可选）
     * @param {Boolean} disabled 是否无效（可选）
     * @param {Number} width 宽度（可选）
     * @param {Number} height 高度（可选）
     * @returns {HTMLInputElement} 文本输入框
     */
    this.createTextInput = function(name, title, value, className, disabled, width, height) {
        var e = null;
        if (name) {
            try {
                e = document.createElement('<input type="text" name="' + name + '"/>');
            } catch (ex) {
            }
            if (!e) {
                e = document.createElement("INPUT");
                e.type = "text";
                e.name = name;
            }
        } else {
            e = document.createElement("INPUT");
            e.type = "text";
        }
        if (title) {
            e.title = title;
            e.alt = title;
        }
        if (disabled) e.disabled = true;
        if (className) e.className = className;
        if (width || width == 0) e.style.width = width + "px";
        if (height || height == 0) e.style.height = height + "px";
        e.value = value ? value : "";
        return e;
    };

    /**
     * 创建图片对象
     * @param {String} src 图片路径
     * @param {String} title 标题（可选）
     * @param {String} className 样式类名称（可选）
     * @param {Number} width 图片宽度（可选）
     * @param {Number} height 图片高度（可选）
     * @returns {HTMLImageElement} 图片对象
     */
    this.createImage = function(src, title, className, width, height) {
        var e = document.createElement("IMG");
        e.src = src;
        if (width || width == 0) e.style.width = width + "px";
        if (height || height == 0) e.style.height = height + "px";
        if (title) {
            e.title = title;
            e.alt = title;
        }
        if (className) e.className = className;
        return e;
    };

    /**
     * 创建透明点图片对象
     * @param {Number} width 图片宽度（可选）
     * @param {Number} height 图片高度（可选）
     * @returns {HTMLElement} 图片对象
     */
    this.createImagePoint = function(width, height) {
        var e = document.createElement("IMG");
        e.src = $G.config["imagesFolder"] + "point.gif";
        e.style.margin = "0px";
        e.style.border = "0px";
        if (width || width == 0) e.width = width;
        if (height || height == 0) e.height = height;
        return e;
    };

    /**
     * 创建表格对象
     * @param {String} width 表格宽度（字符串，不设置则使用自动宽度）
     * @returns {HTMLTableElement} 表格对象
     */
    this.createTable = function(width) {
        var tb = document.createElement("TABLE");
        if (width) {
            width += "";
            tb.width = width;
            if (width.indexOf("%") > 0 || width.indexOf("px") > 0) {
                tb.style.width = width;
            }
        }
        tb.border = 0;
        tb.cellSpacing = 0;
        tb.cellPadding = 0;
        return tb;
    };

    /**
     * 创建 DIV 对象
     * @returns {HTMLDivElement} 对象
     */
    this.createDiv = function() {
        return document.createElement("DIV");
    };

    /**
     * 创建连接对象
     * @param {String} text 文本内容（可选）
     * @param {String} title 标题（可选）
     * @param {String} className 样式类名称（可选）
     * @returns {HTMLAnchorElement} 链接对象
     */
    this.createA = function(text, title, className) {
        var e = document.createElement("A");
        e.href = "javascript:void(0);";
        if (title) e.title = title;
        if (className) e.className = className;
        if (text) e.appendChild(document.createTextNode(text));
        return e;
    };

    /**
     * 创建 IFRAME 对象方法
     * @returns {HTMLIFrameElement} IFRAME 对象
     */
    this.createIframe = function() {
        var iframe = document.createElement("IFRAME");
        iframe.frameBorder = "0";
        iframe.allowTransparency = true;
        return iframe;
    };

    /**
     * 创建简单文本输入下拉框对象
     * @param {Object} data 输入框内显示的数据（字符串或数据对象）
     * @param {Array} dropDatas 下拉窗口显示的数据数组（可以使字符串数组或数据对象数组）
     * @param {Boolean} editable 输入框是否可编辑（可选，默认不可编辑）
     * @param {String} valueKey 设置的数据是对象时，表示输入框表示的真实值属性字段（可选，不设置则把数据作为字符串处理）
     * @param {String} displayKey 设置的数据是对象时，表示在输入框内展示数据的属性字段（可选，不设置则把数据作为字符串处理）
     * @param {Number} textLimit 显示的字符内容最大字节长度（超过则截取并增加 "..." 省略号）
     * @param {Number} width 下拉框外框对象宽度（可选，默认自动扩展）
     * @param {Number} height 下拉框外框对象高度（可选，默认使用样式配置）
     * @param {Number} dropWidth 下拉窗口宽度（可选，默认使用下拉框宽度）
     * @param {Number} dropHeight 下拉窗口高度（可选）
     * @param {Boolean} readOnly 是否只读模式（可选，默认：false；设置为 true 时不显示下拉框）
     * @param {Boolean} acceptInput 是否接受输入的文本内容（可选，只有同时启用编辑模式才有效，默认：false）
     * @returns {$DropBox} 下拉框操作对象
     */
    this.createSelect = function(data, dropDatas, editable, valueKey, displayKey, textLimit, width, height, dropWidth, dropHeight, readOnly, acceptInput) {
        var url = $G.config["modulesFolder"] + "selector.html";
        var dbox = new $DropBox(url, editable, width, height, null, readOnly);
        dbox.setDatas(data, dropDatas, valueKey, displayKey, textLimit, dropWidth, dropHeight, acceptInput);
        return dbox;
    };

    // ------------------------------ 多选框方法 ------------------------------

    /**
     * 改变选择框选择状态
     * @param {String} name 选择框名称
     * @param {Boolean} checked 是否选中
     * @returns {Number} 选择框总数
     */
    this.chooseSelectBox = function(name, checked) {
        var cbs = document.getElementsByName(name);
        var c = cbs.length;
        if (cbs && c > 0) {
            for (var i = 0; i < c; i++) {
                var cb = cbs[i];
                if (cb.checked != checked) {
                    cb.checked = checked;
                }
            }
        }
        return c;
    };

    /**
     * 获取选择框选择状态值数组（返回非 null 数组）
     * @param {String} name 选择框名称
     * @param {String} attributeName 需返回的选择框属性字段
     * @returns {Array} 选择框值数组
     */
    this.getSelectedValues = function(name, attributeName) {
        var rets = [];
        var cbs = document.getElementsByName(name);
        if (!attributeName) attributeName = "value";
        if (cbs && cbs.length > 0) {
            for (var i = 0; i < cbs.length; i++) {
                var cb = cbs[i];
                if (cb && cb.checked) {
                    rets.push($W.getAttribute(cb, attributeName));
                }
            }
        }
        return rets;
    };

    // ------------------------------ 窗口文本方法 ------------------------------

    /**
     * 获取提醒信息文本（返回非 null 文本）
     * @param {String} text 文本内容
     * @returns {String} 附加 FONT 标签后的文本
     */
    this.getNoticeText = function(text) {
        if (text && text.length > 0) return "<font class=\"notice\">" + text + "</font>";
        return "";
    };

    /**
     * 获取灰色信息文本（返回非 null 文本）
     * @param {String} text 文本内容
     * @returns {String} 附加 FONT 标签后的文本
     */
    this.getGrayText = function(text) {
        if (text && text.length > 0) return "<font class=\"gray\">" + text + "</font>";
        return "";
    };

    /**
     * 获取灰色提示信息文本（返回非 null 并附带换行符文本）
     * @param {String} text 文本内容
     * @returns {String} 附加 FONT 标签后的文本
     */
    this.getGrayNote = function(text) {
        if (text && text.length > 0) return "<br/><font class=\"gray\">(" + text + ")</font>";
        return "";
    };

    /**
     * 获取警告提示信息文本（返回非 null 并附带换行符文本）
     * @param {String} text 文本内容
     * @returns {String} 附加 FONT 标签后的文本
     */
    this.getAlarmNote = function(text) {
        if (text && text.length > 0) return "<br/><font class=\"alarm\">(" + text + ")</font>";
        return "";
    };

    // ------------------------------ 进度遮罩方法 ------------------------------

    /**
     * 显示加载图标遮罩（使用 "loading" 样式）
     * @param {String} coverId 被遮罩对象或标识（不设置则不执行遮罩）
     * @returns {HTMLDivElement} 遮罩层对象
     */
    this.showLoading = function(coverId) {
        if (!coverId) return null;
        var img = document.createElement("IMG");
        img.src = $G.config["imagesFolder"] + "point.gif";
        img.title = $G.getText("loading");
        img.alt = img.title;
        img.className = $G.icon.loading;
        img.style.position = "relative";
        img.style.top = "40%";
        var cover = $W.createCover($W.id(coverId), "loading");
        var div = document.createElement("DIV");
        div.style.textAlign = "center";
        div.style.margin = "0px auto";
        div.style.display = "block";
        div.style.position = "relative";
        div.style.width = "100%";
        div.style.height = "100%";
        div.appendChild(img);
        cover.appendChild(div);
        document.body.appendChild(cover);
        return cover;
    };

    /**
     * 创建遮罩层
     * @param {HTMLElement} coverElement 被遮罩对象
     * @param {Boolean} className 遮罩样式名称（可选，不设置则使用 "cover" 样式）
     * @returns {HTMLDivElement} 遮罩层对象
     */
    this.createCover = function(coverElement, className) {
        var cover = document.createElement("DIV");
        cover.style.display = "block";
        cover.style.position = "absolute";
        cover.style.overflow = "auto";
        cover.style.zIndex = $W.getNextZIndex();
        var div = document.createElement("DIV");
        div.className = className ? className : "cover";
        if ($G.navigator.ie && $G.navigator.ie < 7) {
            var cframe = document.createElement("IFRAME");
            cframe.style.left = "0px";
            cframe.style.top = "0px";
            cframe.style.width = "100%";
            cframe.style.height = "100%";
            cframe.style.position = "absolute";
            cframe.style.opacity = "0";
            cframe.style.filter = "alpha(opacity=0)";
            cover.appendChild(cframe);
        }
        cover.appendChild(div);
        var setSize = function(evt) {
            var size, p;
            if ($W.isDocument(coverElement)) {
                size = $W.getDocumentSize(window);
                p = document.body;
            } else if ($W.isWindow(coverElement)) {
                size = $W.getWindowSize(window);
                p = document.body;
            } else {
                size = $W.getSize(coverElement);
                p = coverElement;
            }
            var pos = $W.getPosition(p);
            cover.style.left = pos.left + "px";
            cover.style.top = pos.top + "px";
            cover.style.width = size.width + "px";
            cover.style.height = size.height + "px";
        };
        setSize.call(this);
        window.setTimeout(setSize, 100);
        cover.onWindowResize = setSize;
        $W.attachEvent($W.getTopWindow(), "onresize", cover.onWindowResize);
        return cover;
    };

    // ------------------------------ 渐变显示隐藏方法 ------------------------------

    /**
     * 渐变显示处理
     * @param {HTMLElement} e 需要显示的元素对象
     * @param {Function} onShown 显示完毕后回调（可选，传入 e 元素对象）
     * @param {Number} step 透明变化步长（可选，数值范围：1-100；默认：30）
     * @param {Number} time 单步执行时间间隔（可选，毫秒；默认：25）
     * @returns void
     */
    this.alphaShow = function(e, onShown, step, time) {
        if (!e) return;
        e.style.opacity = 0;
        e.style.filter = "alpha(opacity=0)";
        step = parseInt(step);
        if (isNaN(step)) step = 30;
        if (step > 100) step = 100;
        if (step < 1) step = 1;
        time = parseInt(time);
        if (isNaN(time)) time = 25;
        var alpha = 0;
        function runShow() {
            alpha += step;
            if (alpha >= 100) {
                alpha = 100;
                e.style.opacity = alpha;
                e.style.filter = "alpha(opacity=" + alpha + ")";
                if (onShown) onShown(e);
                return;
            }
            e.style.opacity = alpha / 100;
            e.style.filter = "alpha(opacity=" + alpha + ")";
            window.setTimeout(runShow, time);
        }
        window.setTimeout(runShow, time);
    };

    /**
     * 透明渐变隐藏对象处理
     * @param {HTMLElement} e 需要隐藏的对象（若需要向上移动，必须设置：style - position:"absolute"）
     * @param {Function} onHided 隐藏完毕后回调（可选，传入 e 元素对象）
     * @param {Number} step 透明及移动变化步长（可选，数值范围：1-100；默认：30）
     * @param {Boolean} moveTop 是否在隐藏过程中向上移动（可选，默认：false）
     * @param {Number} time 单步执行时间间隔（可选，毫秒；默认：25）
     * @returns void
     */
    this.alphaHide = function(e, onHided, step, moveTop, time) {
        if (!e) return;
        step = parseInt(step);
        if (isNaN(step)) step = 30;
        if (step > 100) step = 100;
        if (step < 1) step = 1;
        time = parseInt(time);
        if (isNaN(time)) time = 25;
        var top = e.offsetTop;
        var alpha = 100;
        function runHide() {
            alpha -= step;
            if (alpha <= 0) {
                $W.remove(e);
                if (onHided) onHided(e);
                return;
            }
            if (moveTop) e.style.top = (top - (100 - alpha)) + "px";
            e.style.opacity = alpha / 100;
            e.style.filter = "alpha(opacity=" + alpha + ")";
            window.setTimeout(runHide, time);
        }
        window.setTimeout(runHide, time);
    };

    // ========================================== TODO: 显示对话框 ============================================

    /**
     * 创建对话框
     * @param {String} url 对话框页面地址
     * @param {Object} data 需要传输的数据（可选）
     * @param {Boolean} module 是否模态窗口（可选，默认：true）
     * @param {Function} onClose 关闭对话框回调方法（可选，传入：多个参数队列；返回 false 表示禁止关闭对话框）
     * @param {Function} onData 对话框数据回传方法（可选，传入：多个参数队列；可返回数据到对话框）
     * @param {Function} onShown 显示完成回调方法（可选，传入：iframe 对话框对象；不返回数据）
     * @param {String} className 对话框样式（可选，默认使用：shadow）
     * @param {Number} width 宽度（可选，默认自动处理宽度）
     * @param {Number} height 高度（可选，默认自动处理高度）
     * @param {Number} left 左坐标（可选，默认居中）
     * @param {Number} top 顶坐标（可选，默认居中）
     * @returns {HTMLIFrameElement} 创建的对话框 IFRAME 对象，具备 frameDiv 属性表示外层 DIV
     */
    this.createDialog = function(url, data, module, onClose, onData, onShown, className, width, height, left, top) {
        if (!width) width = 0;
        if (!height) height = 0;
        module = (module != false);
        var frameSize = {
            width: width,
            height: height
        };
        left = parseInt(left);
        top = parseInt(top);
        var iframe = $W.createIframe();
        iframe.frameBorder = "0";
        iframe.allowTransparency = true;
        iframe.style.width = width + "px";
        iframe.style.height = height + "px";
        iframe.dialogData = $G.cloneObject(data);
        iframe.isModule = module;
        iframe.frameSize = frameSize;
        iframe.needCenter = (isNaN(left) && width <= 0);
        iframe.needMiddle = (isNaN(top) && height <= 0);
        iframe.onClose = onClose;
        iframe.onData = onData;
        iframe.onShown = onShown;
        iframe.src = url;
        if (isNaN(left) || isNaN(top)) {
            var size = $W.getWindowSize(window);
            var scroll = $W.getScroll(window);
            if (isNaN(left)) left = (size.width - width) / 2 + scroll.left;
            if (isNaN(top)) top = (size.height - height) / 2 + scroll.top;
        }
        var frame = document.createElement("DIV");
        frame.style.position = "absolute";
        frame.style.display = "block";
        if (module) {
            var docsize = $W.getDocumentSize(window);
            iframe.style.position = "absolute";
            iframe.style.left = (left < 0 ? 0 : left) + "px";
            iframe.style.top = (top < 0 ? 0 : top) + "px";
            frame.style.left = "0px";
            frame.style.top = "0px";
            frame.style.width = (docsize.width > 0 ? docsize.width : 0) + "px";
            frame.style.height = (docsize.height > 0 ? docsize.height : 0) + "px";
            frame.style.zIndex = $W.getNextZIndex();
            var cover = document.createElement("DIV");
            cover.className = "cover";
            frame.appendChild(cover);
            frame.appendChild(iframe);
            iframe.className = className ? className : "shadow";
        } else {
            frame.style.left = (left < 0 ? 0 : left) + "px";
            frame.style.top = (top < 0 ? 0 : top) + "px";
            frame.style.width = width + "px";
            frame.style.height = height + "px";
            frame.style.zIndex = $W.getNextZIndex();
            frame.appendChild(iframe);
            frame.className = className ? className : "shadow";
        }
        frame.style.opacity = 0;
        frame.style.filter = "alpha(opacity=0)";
        iframe.frameDiv = frame;
        $W.attachEvent(iframe, "onload,onreadystatechange", function() {
            if (iframe.readyState && iframe.readyState != "complete") return;
            $W.detachEvent(iframe, "onload,onreadystatechange", arguments.callee);
            try {
                if (!(iframe.contentWindow && (typeof iframe.contentWindow.$W) == "object")) {
                    $W.showStatusAlert($G.status.REQUEST_FAILURE, $G.getText("loadPage"));
                    $W.getTopWindow().$W.remove(iframe.frameDiv);
                }
            } catch (ex) {
                $W.showStatusAlert($G.status.REQUEST_FAILURE, $G.getText("loadPage"));
                $W.getTopWindow().$W.remove(iframe.frameDiv);
            }
        });
        return iframe;
    };

    /**
     * 重新设置对话框大小
     * @param {HTMLIFrameElement} iframe 对话框 IFRAME 对象
     * @param {Number} width 宽度（可选）
     * @param {Number} height 高度（可选）
     * @returns void
     */
    this.setDialogSize = function(iframe, width, height) {
        if (!iframe) return;
        if (width && width > 0) {
            iframe.style.width = width + "px";
            if (!iframe.isModule) iframe.frameDiv.style.width = width + "px";
        }
        if (height && height > 0) {
            iframe.style.height = height + "px";
            if (!iframe.isModule) iframe.frameDiv.style.height = height + "px";
        }
    };

    /**
     * 显示对话框
     * @param {String} url 对话框页面地址
     * @param {Object} data 需要传输的数据（可选）
     * @param {Boolean} module 是否模态窗口（可选，默认：true）
     * @param {Function} onClose 关闭对话框回调方法（可选，传入：多个参数队列；返回 false 表示禁止关闭对话框）
     * @param {Function} onData 对话框数据回传方法（可选，传入：多个参数队列；可返回数据到对话框）
     * @param {Function} onShown 显示完成回调方法（可选，传入：iframe 对话框对象；不返回数据）
     * @param {String} className 对话框样式（可选，默认使用：shadow）
     * @param {Number} width 宽度（可选，默认自动处理宽度）
     * @param {Number} height 高度（可选，默认自动处理高度）
     * @param {Number} left 左坐标（可选，默认居中）
     * @param {Number} top 顶坐标（可选，默认居中）
     * @returns {HTMLIFrameElement} 创建的对话框 IFRAME 对象，具备 frameDiv 属性表示外层 DIV
     */
    this.showDialog = function(url, data, module, onClose, onData, onShown, className, width, height, left, top) {
        var funcClose = onClose ? function() {
            if (!window || window.closed) return;
            if (onClose) return onClose.apply(this, arguments);
        } : null;
        var funcData = onData ? function() {
            if (!window || window.closed) return;
            if (onData) return onData.apply(this, arguments);
        } : null;
        var funcShown = onShown ? function() {
            if (!window || window.closed) return;
            if (onShown) onShown.apply(this, arguments);
        } : null;
        var topwin = $W.getTopWindow();
        var rurl = $G.url.getRelativeUrl(url);
        var iframe = topwin.$W.createDialog(rurl, data, module, funcClose, funcData, funcShown, className, width, height, left, top);
        topwin.document.body.appendChild(iframe.frameDiv);
        topwin.$W.alphaShow(iframe.frameDiv);
        if ($G.navigator.ie && $G.navigator.ie < 8) iframe.src = rurl;
        return iframe;
    };

    /**
     * 关闭对话框处理（可传入返回参数队列）
     * @param {HTMLIFrameElement} iframe 对话框 IFRAME 对象
     * @param {Object} datas 需要发送的数据（支持多个数据参数）
     * @returns {Object} onClose 方法回传的数据
     */
    this.closeDialog = function(iframe, datas) {
        if (!iframe || iframe.isClosed) return null;
        var ret = null;
        if (iframe.onClose) {
            var args;
            if (arguments.length > 1) {
                args = $G.argumentsToArray.apply(this, arguments);
                args.shift();
            } else {
                args = [];
            }
            try {
                ret = iframe.onClose.apply(this, args);
            } catch (ex) {
                $W.debug("XXX Error: $W.closeDialog() - " + ex.stack);
            }
            if (ret === false) return ret;
        }
        iframe.isClosed = true;
        var topw = $W.getTopWindow();
        if ($G.navigator.ie) {
            var inp = topw.$W.createTextInput();
            inp.style.width = "10px";
            inp.style.height = "10px";
            inp.style.position = "absolute";
            inp.style.left = "-100px";
            inp.style.top = "-100px";
            topw.document.body.appendChild(inp);
            inp.focus();
            topw.document.body.removeChild(inp);
        }
        topw.$W.alphaHide(iframe.frameDiv);
        return ret;
    };

    /**
     * 判断对话框是否已经被关闭
     * @param {HTMLIFrameElement} iframe 对话框 IFRAME 对象
     * @returns {Boolean} 是否已经被关闭
     */
    this.isDialogClosed = function(iframe) {
        return (!iframe || iframe.isClosed || !iframe.contentWindow || iframe.contentWindow.closed);
    };

    /**
     * 获取传输到对话框的初始数据（返回值不为 null）
     * @returns {Object} 数据对象
     */
    this.getDialogData = function() {
        var iframe = window.frameElement;
        if (iframe) return iframe.dialogData;
        return {};
    };

    /**
     * 设置接收上级窗口传输的数据处理方法
     * @param {Function} func 接收到数据处理（传入从主页面传输的数据，支持多个数据参数；返回需回传的数据）
     * @returns void
     */
    this.setDialogDataListener = function(func) {
        var iframe = window.frameElement;
        if (iframe) iframe.onReceiveData = func;
    };

    /**
     * 发送数据到对话框
     * @param {HTMLIFrameElement} iframe 对话框 IFRAME 对象
     * @param {Object} datas 需要发送的数据（支持多个数据参数）
     * @returns {Object} 回传的数据
     */
    this.sendToDialog = function(iframe, datas) {
        if ($W.isDialogClosed(iframe) || !iframe.onReceiveData) return null;
        var args = $G.argumentsToArray.apply(this, arguments);
        args.shift();
        try {
            return iframe.onReceiveData.apply(this, args);
        } catch (ex) {
            $W.debug("XXX Error: $W.sendToDialog() - " + ex.stack);
        }
        return null;
    };

    /**
     * 发送数据到打开对话框的页面
     * @param {Object} datas 需要发送的数据（支持多个参数）
     * @returns {Object} 打开对话框的页面回传到对话框的数据
     */
    this.sendToDialogParent = function(datas) {
        var iframe = window.frameElement;
        if (!iframe || !iframe.onData) return null;
        try {
            return iframe.onData.apply(this, arguments);
        } catch (ex) {
            $W.debug("XXX Error: $W.sendToDialogParent() - " + ex.stack);
        }
        return null;
    };

    /**
     * 设置对话框接收上级页面事件方法
     * @param {Function} func 接收到事件处理（传入：evt - 从主页面传输的事件；返回：false 表示需要取消事件）
     * @returns void
     */
    this.setDialogEventListener = function(func) {
        var iframe = window.frameElement;
        if (iframe) iframe.onReceiveEvent = func;
    };

    /**
     * 发送事件对象到对话框
     * @param {HTMLIFrameElement} iframe 对话框 IFRAME 对象
     * @param {Event} evt 事件对象
     * @returns {Boolean} 是否取消事件（返回 false 表示需要取消事件）
     */
    this.sendEventToDialog = function(iframe, evt) {
        if (!iframe || !iframe.onReceiveEvent) return true;
        return iframe.onReceiveEvent.call(this, evt);
    };

    // ------------------------- 显示模板页面对话框方法 ----------------------------

    /**
     * 显示增加对话框（根据 fields 配置自动创建表单）
     * @param {String} title 窗口标题（必须）
     * @param {Array} fields 表格配置对象数组（必须，参考：this.fields 数据定义）
     * @param {Function} onGetFieldText 获取字段国际化文本方法（必须，传入：fieldId - 字段名称；返回：文字字符串）
     * @param {Function} onDataReturn 新增数据回调方法（必须）
     *            <ul>
     *            传入：iframe, data, addCallback
     *            <li>iframe - 窗口对象；</li>
     *            <li>data - 被增加的数据对象；</li>
     *            <li>addCallback - 增加完成后的回调方法，传入：success - 是否增加成功；data - 被增加的数据对象</li>
     *            返回：void
     *            </ul>
     * @param {Function} onClose 关闭对话框方法（传入：data - 被增加的数据对象，未增加或增加失败则无数据传入）
     * @param {String} className 对话框样式（可选，默认使用：shadow）
     * @param {Number} width 宽度（可选，默认自动处理宽度）
     * @param {Number} height 高度（可选，默认自动处理高度）
     * @param {Number} left 左坐标（可选，默认居中）
     * @param {Number} top 顶坐标（可选，默认居中）
     * @returns {HTMLIFrameElement} 创建的对话框 IFRAME 对象，具备 frameDiv 属性表示外层 DIV
     */
    this.showAdd = function(title, fields, onGetFieldText, onDataReturn, onClose, className, width, height, left, top) {
        if (!fields || !onGetFieldText || !onDataReturn) return null;
        var url = ($G.config["modulesFolder"] + "add.html");
        var iframe = null;
        iframe = $W.showDialog(url, {
            title: title
        }, true, onClose, function(action) {
            if (action.type == "text") {
                return onGetFieldText.call(this, action.fieldId);
            } else if (action.type == "data") {
                onDataReturn.call(this, iframe, action.data, function(succeed, data) {
                    $W.sendToDialog(iframe, {
                        type: "result",
                        succeed: succeed,
                        data: data
                    });
                });
            }
        }, function() {
            $W.sendToDialog(iframe, {
                type: "init",
                fields: fields
            });
        }, className, width, height, left, top);
        return iframe;
    };

    /**
     * 显示编辑对话框（根据 fields 配置自动创建表单）
     * @param {String} title 窗口标题（必须）
     * @param {Object} data 需要编辑的数据（必须）
     * @param {Array} fields 表格配置对象数组（必须，参考：this.fields 数据定义）
     * @param {Function} onGetFieldText 获取字段国际化文本方法（必须，传入：fieldId - 字段名称；返回：文字字符串）
     * @param {Function} onDataReturn 编辑数据回调方法（必须）
     *            <ul>
     *            传入：iframe, data, editCallback
     *            <li>iframe - 窗口对象；</li>
     *            <li>data - 编辑后的数据对象；</li>
     *            <li>editCallback - 编辑操作完成后的回调方法，传入：success - 是否编辑成功；data - 编辑后的数据对象</li>
     *            返回：void
     *            </ul>
     * @param {Function} onClose 关闭对话框方法（传入：data - 被编辑的数据对象，未编辑或编辑失败则无数据传入）
     * @param {String} className 对话框样式（可选，默认使用：shadow）
     * @param {Number} width 宽度（可选，默认自动处理宽度）
     * @param {Number} height 高度（可选，默认自动处理高度）
     * @param {Number} left 左坐标（可选，默认居中）
     * @param {Number} top 顶坐标（可选，默认居中）
     * @returns {HTMLIFrameElement} 创建的对话框 IFRAME 对象，具备 frameDiv 属性表示外层 DIV
     */
    this.showEdit = function(title, data, fields, onGetFieldText, onDataReturn, onClose, className, width, height, left, top) {
        if (!data || !fields || !onGetFieldText || !onDataReturn) return null;
        var url = ($G.config["modulesFolder"] + "edit.html");
        var iframe = null;
        iframe = $W.showDialog(url, {
            title: title,
            data: data
        }, true, onClose, function(action) {
            if (action.type == "text") {
                return onGetFieldText.call(this, action.fieldId);
            } else if (action.type == "data") {
                onDataReturn.call(this, iframe, action.data, function(succeed, newData) {
                    $W.sendToDialog(iframe, {
                        type: "result",
                        succeed: succeed,
                        data: newData
                    });
                });
            }
        }, function() {
            $W.sendToDialog(iframe, {
                type: "init",
                fields: fields
            });
        }, className, width, height, left, top);
        return iframe;
    };

    /**
     * 显示确认对话框
     * @param {String} message 需要显示的信息
     * @param {Function} onClose 关闭对话框后回调方法（可选，传入：$G.button.xxxx 点击按钮类型）
     * @param {String} icon 窗口图标样式（可选，对应：$G.icon.xxxx）
     * @param {Number} buttons 按钮类型（可选，对应：$G.button.xxxx，多个使用 | 进行或运算）
     * @param {String} title 标题（可选，默认使用系统名称）
     * @param {Boolean} noModule 是否非模态化窗口（可选，默认使用模态窗口）
     * @param {Number} focusButton 焦点按钮类型（可选：对应：$G.button.xxxx）
     * @returns {HTMLIFrameElement} 创建的对话框 IFRAME 对象，具备 frameDiv 属性表示外层 DIV
     */
    this.showAlert = function(message, onClose, icon, buttons, title, noModule, focusButton) {
        var data = {
            title: (title ? title : $G.getText("title")),
            message: message,
            types: buttons,
            focusType: focusButton,
            icon: (icon ? icon : $G.icon.info)
        };
        var url = ($G.config["modulesFolder"] + "alert.html");
        return $W.showDialog(url, data, noModule ? false : true, onClose);
    };

    /**
     * 显示状态信息处理
     * @param {Number} status 状态码（对应：$G.status.XXXX）
     * @param {String} action 执行的操作信息字符串
     * @param {String} note 提示信息（可选）
     * @param {Function} onClose 关闭对话框后回调方法，传入点击按钮类型（可选，对应：$G.button.xxxx）
     * @returns void
     */
    this.showStatusAlert = function(status, action, note, onClose) {
        status = $G.status.fix(status);
        var msg = $G.getText("status" + status, action, status, $W.getGrayNote(note));
        if (status == $G.status.REQUEST_FAILURE) {
            window.alert($G.deleteHtmlTag(msg));
            if (onClose) onClose($G.button.ok);
        } else {
            $W.showAlert(msg, onClose, $G.icon.alarm);
        }
    };

    /**
     * 显示提示信息
     * @param {String} message 信息文本
     * @param {String} icon 图标样式（可选，对应：$G.icon.tipXXXX）
     * @param {Number} hideTime 隐藏时间（可选，毫秒，不设置或设置为 null 则自动根据字节长度自动计算）
     * @param {Number} left 左坐标（可选，默认居中）
     * @param {Number} top 顶坐标（可选，默认居中）
     * @returns void
     */
    this.showTip = function(message, icon, hideTime, left, top) {
        var topw = $W.getTopWindow();
        if (typeof topw.$W == "object") {
            topw.$W.createTip(message, icon, hideTime, left, top);
        } else {
            $W.createTip(message, icon, hideTime, left, top);
        }
    };

    /**
     * 创建提示信息框
     * @param {String} message 信息文本
     * @param {String} icon 图标样式（可选，对应：$G.icon.tipXXXX）
     * @param {Number} hideTime 隐藏时间（可选，毫秒，不设置或设置为 null 则自动根据字节长度自动计算）
     * @param {Number} left 左坐标（可选，默认居中）
     * @param {Number} top 顶坐标（可选，默认居中）
     * @returns {HTMLDivElement} 被创建的提示信息元素对象
     */
    this.createTip = function(message, icon, hideTime, left, top) {
        var tip = document.createElement("DIV");
        tip.style.position = "absolute";
        tip.style.display = "block";
        tip.style.opacity = 0;
        tip.style.filter = "alpha(opacity=0)";
        tip.style.zIndex = $W.getNextZIndex();
        tip.className = "tips";
        var img = document.createElement("IMG");
        img.src = $G.config["imagesFolder"] + "point.gif";
        img.className = (icon ? icon : $G.icon.tipInfo);
        tip.appendChild(img);
        var msgDiv = document.createElement("DIV");
        msgDiv.className = "message";
        msgDiv.innerHTML = message;
        msgDiv.noWrap = true;
        tip.appendChild(msgDiv);
        document.body.appendChild(tip);
        left = parseInt(left);
        top = parseInt(top);
        if (isNaN(left) || isNaN(top)) {
            var size = $W.getWindowSize(window);
            var scroll = $W.getScroll(window);
            if (isNaN(left)) left = (size.width - tip.offsetWidth) / 2 + scroll.left;
            if (isNaN(top)) top = (size.height - tip.offsetHeight) / 2 + scroll.top;
        }
        tip.style.top = top + "px";
        tip.style.left = left + "px";
        hideTime = parseInt(hideTime);
        if (!hideTime || isNaN(hideTime)) {
            hideTime = 1500;
            var len = $G.getBytesLength($G.deleteHtmlTag(message));
            hideTime += 100 * len;
        }
        var hidding = false;
        var intervalId = 0;
        function beginHide(time) {
            cancelHide();
            intervalId = window.setTimeout(function() {
                hidding = true;
                $W.alphaHide(tip, null, 5, true, 20);
            }, time);
        }
        function cancelHide() {
            if (intervalId != 0) {
                window.clearInterval(intervalId);
                intervalId = 0;
            }
        }
        $W.attachEvent(tip, "onmouseover", function(evt) {
            if (hidding) return;
            cancelHide();
        });
        $W.attachEvent(tip, "onmouseout", function(evt) {
            if (hidding) return;
            beginHide(1000);
        });
        $W.alphaShow(tip, function(e) {
            beginHide(hideTime);
        });
        return tip;
    };

    // ------------------------------ 加载进度条方法 ------------------------------

    /**
     * 显示进度条
     * @param {String} title 标题信息
     * @param {String} message 进度信息
     * @param {Number} pos 当前进度位置（整数）
     * @param {Number} maxPos 最大进度值（整数）
     * @param {Function} onShown 进度条显示完毕回调（只有显示完毕后才能接受到后续发送的数据，传入：iframe 对话框对象）
     * @param {Function} onData 用户操作状态回调，传入：iframe 对话框对象 和 $G.button.xxxx 用户操作状态<br/> 数据格式：function(iframe, type){}
     * @param {Number} titleButton 标题栏按钮类型（可选，对应：$G.button.titleXXXX，默认只显示关闭按钮）
     * @param {Boolean} noModule 是否非模态化窗口（可选，默认使用模态窗口）
     * @returns {HTMLIFrameElement} 创建的对话框 IFRAME 对象，具备 frameDiv 属性表示外层 DIV
     */
    this.showLoadingBar = function(title, message, pos, maxPos, onShown, onData, titleButton, noModule) {
        var data = {
            title: title,
            message: message,
            pos: pos,
            maxPos: maxPos,
            titleButton: titleButton
        };
        var url = ($G.config["modulesFolder"] + "loading.html");
        return $W.showDialog(url, data, noModule ? false : true, null, onData, onShown);
    };

    /**
     * 发送进度数据到进度条
     * @param {HTMLIFrameElement} iframe 对话框 IFRAME 对象（必须）
     * @param {String} message 进度信息（可选，设置为 null 表示不改变）
     * @param {Number} pos 当前进度位置（可选，整数，设置为 null 表示不改变）
     * @param {Boolean} closeMark 是否指示关闭进度条（可选，默认：false）
     * @param {String} title 标题信息（可选，设置为 null 表示不改变）
     * @returns {Object} 回传的数据
     */
    this.sendToLoadingBar = function(iframe, message, pos, closeMark, title) {
        var data = {
            title: title,
            message: message,
            pos: pos,
            closeMark: closeMark
        };
        return $W.sendToDialog(iframe, data);
    };

    // ----------------------------------- 调试方法定义 -------------------------------------

    /**
     * 显示调试信息<br/> 如果将 arguments 数组传递给此方法，则数组中包含的参数会转换成字符串，然后在返回前在字符串中按顺序替代占位符 "{0}"、"{1}" 等。
     * @param {String} message 信息，可包含：{0}..{n} 占位符
     * @param {Object} parameters 需要替换{0}..{n}的参数队列
     * @returns void
     */
    this.debug = function(message, parameters) {
        if (!$G.config.debugMode) return;
        var win = $W.getTopWindow();
        if (typeof win.$W != "object") return;
        var box = win.$W.createDebuger();
        if (!box) return;
        var tb = box.tb;
        if (!tb) return;
        var msg = win.$G.formatString.apply($G, arguments);
        var t = "[" + $G.formatDate(new Date()) + "] " + msg;
        if (tb.value.length == 0) {
            tb.value = t;
        } else {
            tb.value += "\n" + t;
        }
        try {
            tb.scrollTop = tb.scrollHeight;
        } catch (ex) {
        }
        if (win.$W.windowLoaded()) {
            win.$W.debugSchedule(win, tb);
        }
    };

    /**
     * 计划显示调试分隔符
     * @param {Window} win 窗口对象
     * @param table 表格对象
     * @returns void
     */
    this.debugSchedule = function(win, table) {
        if (table && !table.scheduleToNote) {
            table.scheduleToNote = true;
            win.setTimeout(function() {
                table.scheduleToNote = false;
                table.progressIndex++;
                var t = "[" + $G.formatDate(new Date()) + "] -------------------" + table.progressIndex + "--------------------";
                if (table.value.length == 0) {
                    table.value = t;
                } else {
                    table.value += "\n" + t;
                }
                table.scrollTop = table.scrollHeight;
            }, 1000);
        }
    };

    /**
     * 创建调试器
     * @returns {Object} 调试器数据对象
     */
    this.createDebuger = function() {
        var box = window.boxDebugger;
        if (box) return box;
        box = document.createElement("DIV");
        box.style.display = "block";
        box.style.left = "0px";
        box.style.width = "100%";
        box.style.height = $G.config["debugerHeight"] + "px";
        box.style.position = "absolute";
        box.style.backgroundColor = "#6985a1";
        var tb = document.createElement("TEXTAREA");
        tb.style.wordWrap = "normal";
        tb.style.position = "absolute";
        tb.style.fontSize = "11px";
        tb.style.border = "0px";
        tb.style.padding = "0px";
        tb.style.margin = "0px";
        tb.style.left = "2px";
        tb.style.top = "2px";
        tb.style.backgroundColor = "#000";
        tb.style.color = "#FFF";
        tb.progressIndex = 0;
        tb.scheduleToNote = false;
        window.boxDebugger = {
            box: box,
            tb: tb
        };
        $W.onload(function() {
            try {
                document.body.appendChild(box);
                box.style.top = $W.getWindowSize(window).height + "px";
                tb.style.width = (box.clientWidth - 4) + "px";
                tb.style.height = (box.clientHeight - 4) + "px";
                box.appendChild(tb);
                $W.attachEvent(tb, "onkeydown", function(evt) {
                    var kc = $W.getEventKeyCode(evt);
                    if (kc != 13) return;
                    var txt = tb.value;
                    var pos = txt.lastIndexOf("\n");
                    txt = txt.substr(pos + 1);
                    if (txt && txt.indexOf("js:") == 0) {
                        txt = txt.substr(3);
                        try {
                            var ret = eval(txt);
                            if (ret || ret == 0) {
                                tb.value += " -> Execute Result: " + ret;
                            } else {
                                tb.value += " -> Execute successfully.";
                            }
                        } catch (ex2) {
                            tb.value += " -> Execute failed: " + ex2.message;
                        }
                    }
                });
            } catch (ex) {
            }
        });
        $W.attachEvent(window, "onresize", function(evt) {
            var scroll = $W.getScroll(window);
            box.style.top = $W.getWindowSize(window).height + scroll.top + "px";
            box.style.left = scroll.left + "px";
            tb.style.width = (box.clientWidth - 4) + "px";
            tb.style.height = (box.clientHeight - 4) + "px";
        });
        $W.attachEvent(window, "onscroll", function(evt) {
            var scroll = $W.getScroll(window);
            box.style.top = $W.getWindowSize(window).height + scroll.top + "px";
            box.style.left = scroll.left + "px";
        });
        return window.boxDebugger;
    };

}

// ============================================ TODO: 公共对象定义 ===========================================

/**
 * COOKIE 处理对象
 * @param {String} path COOKIE 有效路径（可选）
 */
function $Cookie(path) {

    /**
     * COOKIE 路径
     */
    var cpath = path ? path : "/";

    /**
     * 设置 COOKIE 数据
     * @param {String} key 键
     * @param {String} value 值
     * @param {Number} timeout 有效期（秒，不设置表示当前进程有效）
     * @returns {$Cookie} 对象引用
     */
    this.set = function(key, value, timeout) {
        if (!key || key.length < 1) return this;
        var c = key + "=" + escape(value) + "; path=" + cpath;
        if (typeof timeout == "number") {
            var d = new Date();
            d.setTime(d.getTime() + timeout * 1000);
            c += "; expires=" + d.toUTCString();
        }
        document.cookie = c;
        return this;
    };

    /**
     * 获取 COOKIE 值
     * @param {String} key 键
     * @returns {String} 值
     */
    this.get = function(key) {
        if (!key || key.length < 1) return "";
        var co = document.cookie;
        var arg = key + "=";
        var alen = arg.length;
        var clen = co.length;
        var i = 0;
        while (i < clen) {
            var j = i + alen;
            if (co.substring(i, j) == arg) {
                var endstr = co.indexOf(";", j);
                if (endstr == -1) endstr = clen;
                return unescape(co.substring(j, endstr));
            }
            i = co.indexOf(" ", i) + 1;
            if (i == 0) break;
        }
        return "";
    };

    /**
     * 删除 COOKIE 数据
     * @param {String} key 键
     * @returns {$Cookie} 对象引用
     */
    this.remove = function(key) {
        var d = new Date();
        document.cookie = key + "=; path=" + cpath + "; expires=" + d.toUTCString();
        return this;
    };

}

/**
 * 哈希表类
 */
function $HashMap() {

    /**
     * 哈希表数组
     */
    var _map = {};
    /**
     * 元素总数量
     */
    var _mc = 0;

    /**
     * 清空数据
     * @returns {$HashMap} 对象引用
     */
    this.clear = function() {
        _map = {};
        _mc = 0;
        return this;
    };

    /**
     * 判断是否存在键
     * @param {String} key 键
     * @returns {Boolean} 是否存在数据
     */
    this.containsKey = function(key) {
        return _map.hasOwnProperty(key);
    };

    /**
     * 判断是否存在值
     * @param {Object} value 值
     * @returns {Boolean} 是否存在数据
     */
    this.containsValue = function(value) {
        var contains = false;
        for ( var i in _map) {
            if (_map[i] === value) {
                contains = true;
                break;
            }
        }
        return contains;
    };

    /**
     * 获取键对应数据
     * @param {String} key 键
     * @returns {any} Object 值
     */
    this.get = function(key) {
        return _map[key];
    };

    /**
     * 判断哈希表是否无数据
     * @returns {Boolean} 是否有数据
     */
    this.isEmpty = function() {
        return (_mc == 0);
    };

    /**
     * 获取键数组
     * @returns {Array} 键数组
     */
    this.keys = function() {
        var keys = [];
        for ( var i in _map) {
            keys.push(i);
        }
        return keys;
    };

    /**
     * 增加数据到哈希表（存在则替换）
     * @param {String} key 键
     * @param {Object} value 值
     * @returns {$HashMap} 对象引用
     */
    this.put = function(key, value) {
        if (!key) return this;
        if (!_map.hasOwnProperty(key)) _mc++;
        _map[key] = value;
        return this;
    };

    /**
     * 移除键及数据
     * @param {String} key 键
     * @returns {Object} 被移除的数据
     */
    this.remove = function(key) {
        if (!key || !_map.hasOwnProperty(key)) return null;
        _mc--;
        var rtn = _map[key];
        try {
            delete _map[key];
        } catch (ex) {
        }
        return rtn;
    };

    /**
     * 获取数据总数
     * @returns {Number} 总数
     */
    this.size = function() {
        return _mc;
    };

    /**
     * 遍历所有数据
     * @param {Function} func 遍历处理方法，传入参数: key - 键；value - 值；返回 false 表示停止遍历
     * @returns void
     */
    this.foreach = function(func) {
        if (!func) return;
        for ( var i in _map) {
            if (func(i, _map[i]) === false) break;
        }
    };

    /**
     * 把哈希表转换为字符串
     * @returns {String} 字符串
     */
    this.toString = function() {
        return new $JSON().toJSONString(_map);
    };

    /**
     * 获取值数组（返回非 null 数组）
     * @returns {Array} 值数组
     */
    this.values = function() {
        var values = [];
        for ( var i in _map) {
            values.push(_map[i]);
        }
        return values;
    };

}

/**
 * URL 解析对象类
 * @param {Boolean} lcase 是否使用小写（默认：false）
 */
function $Url(lcase) {

    /**
     * 页面 URL 连接地址（不带参数）
     */
    var baseUrl = "";
    /**
     * 参数集合（? 后的参数）
     */
    var paramsMap = new $HashMap();
    /**
     * 定位参数集合（# 后的参数）
     */
    var hashMap = new $HashMap();
    /**
     * 是否使用小写参数
     */
    var lowerCase = lcase ? true : false;

    // ---------------- 解析参数集合方法 ----------------------

    /**
     * 解析参数数据方法
     * @param {String} str 参数字符串
     * @returns {$HashMap} 参数集合对象
     */
    var parseParamString = function(str) {
        var map = new $HashMap();
        if (str && str.length > 0) {
            var spes = str.split(/[\\&]/);
            if (spes && spes.length > 0) {
                for (var i = 0, c = spes.length; i < c; i++) {
                    var sc = spes[i].split(/[\\=]/);
                    if (sc && sc.length > 1) {
                        var key = sc[0];
                        if (lowerCase) {
                            key = key.toLowerCase();
                        }
                        var value = "";
                        for (var n = 1, m = sc.length; n < m; n++) {
                            if (n > 1) {
                                value += "=";
                            }
                            value += sc[n];
                        }
                        try {
                            value = $G.decodeUrl(value);
                        } catch (e) {
                        }
                        map.put(key, value);
                    }
                }
            }
        }
        return map;
    };

    // ---------------- 解析 URL 方法 ----------------------

    /**
     * 解析 URL 字符串
     * @param {String} url URL 完整字符串（包含参数字段）
     * @returns void
     */
    this.parse = function(url) {
        this.reset();
        if (!url || url.length == 0) return;
        var searchPos = -1;
        var hashPos = -1;
        var searchStr = "";
        var hashStr = "";
        var pageUrl = url;

        searchPos = url.indexOf("?");
        if (searchPos != -1) {
            pageUrl = url.substring(0, searchPos);
            searchStr = url.substring(searchPos + 1);
            hashPos = searchStr.indexOf("#");
            if (hashPos != -1) {
                hashStr = searchStr.substring(hashPos + 1);
                searchStr = searchStr.substring(0, hashPos);
            }
        } else {
            hashPos = url.indexOf("#");
            if (hashPos != -1) {
                pageUrl = url.substring(0, hashPos);
                hashStr = url.substring(hashPos + 1);
            }
        }
        baseUrl = pageUrl;
        paramsMap = parseParamString.call(this, searchStr);
        hashMap = parseParamString.call(this, hashStr);
    };

    /**
     * 重置数据
     * @returns void
     */
    this.reset = function() {
        baseUrl = "";
        paramsMap.clear();
        hashMap.clear();
    };

    // ---------------- 设置地址方法 ----------------------

    /**
     * 使用当前链接替换当前窗口链接
     * @returns {Boolean} 是否被替换为新链接
     */
    this.replaceThisUrl = function() {
        var url = window.location.href;
        var newUrl = this.getUrl();
        if (url != newUrl) {
            window.location.href = newUrl;
            return true;
        }
        return false;
    };

    /**
     * 使用当前 # 定位字符串替换当前窗口定位字符串
     * @returns {Boolean} 是否被替换
     */
    this.replaceThisHash = function() {
        var hash = window.location.hash;
        var nowHash = this.getHashString();
        if (nowHash && nowHash.length > 0) nowHash = "#" + nowHash;
        if (hash != nowHash) {
            window.location.hash = nowHash;
            return true;
        }
        return false;
    };

    // ---------------- 地址获取方法 ----------------------

    /**
     * 获取完整的 URL 字符串
     * @returns {String} 字符串
     */
    this.getUrl = function() {
        return this.toString();
    };

    /**
     * 获取 ? 参数字符串
     * @returns {String} 字符串
     */
    this.getParametersString = function() {
        var pars = [];
        var lc = lowerCase;
        paramsMap.foreach(function(k, v) {
            if (!k || k.length == 0) return;
            if (v && (typeof v == "function")) return;
            if (lc) k = k.toLowerCase();
            try {
                pars.push(k + "=" + $G.encodeUrl(v));
            } catch (e) {
                pars.push(k + "=" + v);
            }
        });
        return pars.join("&").toString();
    };

    /**
     * 获取 # 参数字符串
     * @returns {String} 字符串
     */
    this.getHashString = function() {
        var pars = [];
        var lc = lowerCase;
        hashMap.foreach(function(k, v) {
            if (!k || k.length == 0) return;
            if (v && (typeof v == "function")) return;
            if (lc) k = k.toLowerCase();
            try {
                pars.push(k + "=" + $G.encodeUrl(v));
            } catch (e) {
                pars.push(k + "=" + v);
            }
        });
        return pars.join("&").toString();
    };

    /**
     * 把对象转换为 URL 地址字符串
     * @returns {String} URL 地址字符串
     */
    this.toString = function() {
        var ret = baseUrl;
        if (paramsMap.size() > 0) {
            ret += "?" + this.getParametersString();
        }
        if (hashMap.size() > 0) {
            ret += "#" + this.getHashString();
        }
        return ret;
    };

    /**
     * 获取页面相对根目录地址（相对网站跟目录，不附带参数）
     * @returns {String} 页面地址
     */
    this.getPageRootUrl = function() {
        return baseUrl;
    };

    /**
     * 获取页面相对根目录地址（附带参数）
     * @returns {String} 页面地址
     */
    this.getPageRootFullUrl = function() {
        return window.location.href;
    };

    /**
     * 根据当前页面地址获取相对地址的完整地址
     * @param {String} relativeUrl 相对地址
     * @returns {String} 完整地址
     */
    this.getRelativeUrl = function(relativeUrl) {
        if (!relativeUrl) return "";
        if (relativeUrl.startsWith("/") || $G.isURLFormat(relativeUrl)) return relativeUrl;
        if (relativeUrl.indexOf("./") == 0) {
            relativeUrl = relativeUrl.substr(2);
        }
        var re = /\.\.\//gi;
        var r = relativeUrl.match(re);
        var path = baseUrl;
        for (var i = 0, c = (r ? r.length : 0) + 1; i < c; i++) {
            var pos = path.lastIndexOf("/");
            if (pos != -1) {
                path = path.substring(0, pos);
            } else {
                break;
            }
        }
        if (!path.endsWith("/")) {
            path += "/";
        }
        var rurl = relativeUrl.replace(re, "");
        return path + rurl;
    };

    // ---------------- 查找参数处理方法 ----------------------

    /**
     * 清理 URL 参数数据
     * @returns void
     */
    this.clearParameters = function() {
        paramsMap.clear();
    };

    /**
     * 设置 URL 参数数据
     * @param {String} name 参数名称
     * @param {String} value 参数值
     * @returns void
     */
    this.setParameter = function(name, value) {
        if (!name || name.length == 0) return;
        if (lowerCase) name = name.toLowerCase();
        if (!value) value = "";
        paramsMap.put(name, value);
    };

    /**
     * 获取 URL 参数数据（不存在则返回 defaultValue）
     * @param {String} name 参数名称
     * @param {String} defaultValue 默认返回值
     * @returns {String} 参数值
     */
    this.getParameter = function(name, defaultValue) {
        if (!name || name.length == 0) return defaultValue;
        if (lowerCase) name = name.toLowerCase();
        var value = paramsMap.get(name);
        return (value && value.length > 0) ? value : defaultValue;
    };

    // ---------------- 定位参数处理方法 ----------------------

    /**
     * 清理 # 参数数据
     * @returns void
     */
    this.clearHashParams = function() {
        hashMap.clear();
    };

    /**
     * 设置 # 参数数据
     * @param {String} name 参数名称
     * @param {String} value 参数值
     * @returns void
     */
    this.setHashParameter = function(name, value) {
        if (!name || name.length == 0) return;
        if (lowerCase) name = name.toLowerCase();
        if (!value) value = "";
        hashMap.put(name, value);
    };

    /**
     * 获取 # 参数数据（不存在则返回 defaultValue）
     * @param {String} name 参数名称
     * @param {String} defaultValue 默认返回值
     * @returns {String} 参数值
     */
    this.getHashParameter = function(name, defaultValue) {
        if (!name || name.length == 0) return defaultValue;
        if (lowerCase) name = name.toLowerCase();
        var value = hashMap.get(name);
        return (value && value.length > 0) ? value : defaultValue;
    };

}

// ----------------------------------------- JSON数据处理 -----------------------------------------

/**
 * 把日期转换为 JSON 数据
 * @returns {Number} 日期时间值
 */
Date.prototype.toJSON = function() {
    return this.getTime();
};

/**
 * 把字符串或数字布尔值转换为 JSON 数据
 * @returns {any} 获取值
 */
String.prototype.toJSON = Number.prototype.toJSON = Boolean.prototype.toJSON = function() {
    return this.valueOf();
};

/**
 * JSON 处理对象
 */
function $JSON() {
    var gap = "", indent = "", rep = null;
    var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g;
    var escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g;
    var meta = {
        '\b': '\\b',
        '\t': '\\t',
        '\n': '\\n',
        '\f': '\\f',
        '\r': '\\r',
        '"': '\\"',
        '\\': '\\\\'
    };

    /**
     * 数值补零处理
     * @param {Number} n 数值
     * @returns {String} 字符串
     */
    function f(n) {
        return n < 10 ? '0' + n : n;
    }

    /**
     * 引用字符串处理
     * @param {String} str 测试字符串
     * @returns {String} 修正后字符串
     */
    function quote(str) {
        escapable.lastIndex = 0;
        return escapable.test(str) ? '"' + str.replace(escapable, function(a) {
            var c = meta[a];
            return typeof c === 'string' ? c : '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
        }) + '"' : '"' + str + '"';
    }

    /**
     * 字符串处理
     * @param {String} key 键
     * @param {Object} holder 对象
     * @returns {String} 字符串
     */
    function str(key, holder) {
        var i, k, v, length, mind = gap, partial, value = holder[key];
        if (value && typeof value === 'object' && typeof value.toJSON === 'function') {
            value = value.toJSON(key);
        }
        if (typeof rep === 'function') {
            value = rep.call(holder, key, value);
        }
        switch (typeof value) {
            case 'string':
                return quote(value);
            case 'number':
                return isFinite(value) ? String(value) : 'null';
            case 'boolean':
            case 'null':
                return String(value);
            case 'object':
                if (!value) { return 'null'; }
                gap += indent;
                partial = [];
                if (Object.prototype.toString.apply(value) === '[object Array]') {
                    length = value.length;
                    for (i = 0; i < length; i += 1) {
                        partial[i] = str(i, value) || 'null';
                    }
                    v = partial.length === 0 ? '[]' : gap ? '[\n' + gap + partial.join(',\n' + gap) + '\n' + mind + ']' : '[' + partial.join(',') + ']';
                    gap = mind;
                    return v;
                }
                if (rep && typeof rep === 'object') {
                    length = rep.length;
                    for (i = 0; i < length; i += 1) {
                        if (typeof rep[i] === 'string') {
                            k = rep[i];
                            v = str(k, value);
                            if (v) {
                                partial.push(quote(k) + (gap ? ': ' : ':') + v);
                            }
                        }
                    }
                } else {
                    for ( var x in value) {
                        if (Object.prototype.hasOwnProperty.call(value, x)) {
                            v = str(x, value);
                            if (v) {
                                partial.push(quote(x) + (gap ? ': ' : ':') + v);
                            }
                        }
                    }
                }
                v = partial.length === 0 ? '{}' : gap ? '{\n' + gap + partial.join(',\n' + gap) + '\n' + mind + '}' : '{' + partial.join(',') + '}';
                gap = mind;
                return v;
        }
    }

    /**
     * 把对象转换为 JSON 字符串
     * @param {Object} value 需要转换的对象
     * @param {Object} replacer 替换对象（可选）
     * @param {String} space 间隔字符串（可选）
     * @returns {String} JSON 结果
     */
    this.toJSONString = function(value, replacer, space) {
        if (!value) return "";
        var i;
        gap = '';
        indent = '';
        if (typeof space === 'number') {
            for (i = 0; i < space; i += 1) {
                indent += ' ';
            }
        } else if (typeof space === 'string') {
            indent = space;
        }
        rep = replacer;
        if (replacer && typeof replacer !== 'function' && (typeof replacer !== 'object' || typeof replacer.length !== 'number')) { throw new Error('JSON.stringify'); }
        return "" + str('', {
            '': value
        });
    };

    /**
     * 把字符串转换为对象
     * @param {String} text JSON 字符串
     * @param {Function} reviver 接收器对象（可选，传入：holder, key, value）
     * @returns {Object} 数据对象
     */
    this.fromJSONString = function(text, reviver) {
        if (!text) return null;
        var j;
        function walk(holder, key) {
            var v, value = holder[key];
            if (value && typeof value === 'object') {
                for ( var k in value) {
                    if (Object.prototype.hasOwnProperty.call(value, k)) {
                        v = walk(value, k);
                        if (v !== undefined) {
                            value[k] = v;
                        } else {
                            delete value[k];
                        }
                    }
                }
            }
            return reviver.call(holder, key, value);
        }
        text = String(text);
        cx.lastIndex = 0;
        if (cx.test(text)) {
            text = text.replace(cx, function(a) {
                return '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
            });
        }
        if (/^[\],:{}\s]*$/.test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@').replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']').replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {
            j = eval('(' + text + ')');
            return typeof reviver === 'function' ? walk({
                '': j
            }, '') : j;
        }
        throw new SyntaxError('JSON.parse');
    };

}

// ========================================= 数据传输对象 =======================================

/**
 * 数据传输状态代码
 */
function $StatusCode() {

    /**
     * 修正状态码
     * @param {Number} status 状态码
     * @returns {Number} 修正后的状态码
     */
    this.fix = function(status) {
        return (status > 0 && status < 9) ? status : this.OTHERS;
    };

    /**
     * 成功状态
     */
    this.OK = 1;
    /**
     * 方法调用失败
     */
    this.METHOD_FAILURE = 2;
    /**
     * 请求数据失败
     */
    this.REQUEST_FAILURE = 3;
    /**
     * 数据格式无效
     */
    this.DATA_INVALID = 4;
    /**
     * 需要登录
     */
    this.LOGIN_REQUIRED = 5;
    /**
     * 权限验证失败
     */
    this.LICENSE_FORBIDDEN = 6;
    /**
     * 处理数据时遇到错误
     */
    this.EXPECTATION = 7;
    /**
     * 其它错误
     */
    this.OTHERS = 8;

}

/**
 * 数据传输结果类定义
 */
function $Result() {

    /**
     * 结果状态
     */
    this.status = 0;
    /**
     * 信息内容（不为 null）
     */
    this.message = "";
    /**
     * 回传的数据
     */
    this.data = null;

    /**
     * 对象引用
     */
    var _result = this;

    // --------------- 对象方法定义 ---------------------

    /**
     * 初始化数据
     * @param {Number} status 结果状态
     * @param {String} message 错误信息
     * @param {Object} data 结果数据
     * @returns {$Result} 结果对象
     */
    this.init = function(status, message, data) {
        _result.status = $G.status.fix(status);
        _result.message = (message ? message : "");
        _result.data = data;
        return _result;
    };

    /**
     * 判断是否提交成功
     * @returns {Boolean} 是否提交成功
     */
    this.succeed = function() {
        return _result.status == $G.status.OK;
    };

    /**
     * 设置状态信息处理
     * @param {Number} status 状态码
     * @param {String} message 信息内容
     * @returns {$Result} 结果对象
     */
    this.setStatus = function(status, message) {
        _result.status = $G.status.fix(status);
        _result.message = (message ? message : "");
        return _result;
    };

}

/**
 * 数据处理类定义
 * @param {String} coverId 发送数据是需要显示加载进度遮罩的元素标识（可选，默认不进行遮罩）
 */
function $Data(coverId) {

    /**
     * 数据对象引用
     */
    var _data = this;

    /**
     * 创建 HTTP 传输对象方法
     * @returns {XMLHttpRequest} 数据请求对象
     */
    var createHttp = function() {
        var obj = null;
        if ($G.navigator.ie) {
            var msxmls = ["Msxml2.XMLHTTP.6.0", "MSXML2.XMLHTTP.5.0", "Msxml2.XMLHTTP.4.0", "Msxml2.XMLHTTP.3.0", "Msxml2.XMLHTTP.2.6", "Msxml2.XMLHTTP", "Microsoft.XMLHTTP"];
            var l = msxmls.length;
            for (var i = 0; i < l; i++) {
                try {
                    obj = new ActiveXObject(msxmls[i]);
                    if (obj) {
                        break;
                    }
                } catch (e) {
                    obj = null;
                }
            }
        } else {
            if (typeof XMLHttpRequest != "undefined") {
                try {
                    obj = new XMLHttpRequest();
                    if (obj.overrideMimeType) {
                        obj.overrideMimeType("text/xml");
                    }
                } catch (e) {
                    obj = null;
                }
            } else {
                try {
                    obj = window.createRequest();
                } catch (e) {
                    obj = null;
                }
            }
        }
        return obj;
    };

    /**
     * 创建请求结果数据方法
     * @param {String} cmd 指令值
     * @param {String} ctrl 控制字
     * @param {String} version 数据版本
     * @param {Number} status 状态码
     * @param {String} message 状态信息
     * @param {String} text 数据结果字符串
     * @param {String} asyncKey 异步模式标识
     * @param {Boolean} showDebug 是否显示调试信息
     * @returns {$Result} 结果对象
     */
    var createResult = function(cmd, ctrl, version, status, message, text, asyncKey, showDebug) {
        var result = new $Result();
        if (status == $G.status.OK) {
            if (text && text.length > 0) {
                var ary = text.split("|");
                var len = ary.length;
                if (len >= 3) {
                    var sc = $G.toInt(ary[0], $G.status.OTHERS);
                    var msg = ary[1];
                    var txt = "";
                    if (len == 3) {
                        txt = ary[2];
                    } else {
                        for (var i = 2; i < len; i++) {
                            if (i > 2) txt += "|";
                            txt += ary[i];
                        }
                    }
                    if (msg == "null") msg = "";
                    if (txt == "null") txt = null;
                    result.init(sc, msg, new $JSON().fromJSONString(txt));
                } else {
                    result.init($G.status.DATA_INVALID, $G.getText("dataNotMatch"), null);
                }
            } else {
                result.init($G.status.REQUEST_FAILURE, "", null);
            }
        } else {
            result.init(status, message, null);
        }
        if (result.succeed()) {
            if (showDebug) $W.debug($G.getText("recvInfo"), cmd, ctrl, version, asyncKey, status, message, text);
        } else {
            $W.debug($G.getText("recvInfo"), cmd, ctrl, version, asyncKey, status, message, text);
        }
        return result;
    };

    /**
     * 发送数据处理
     * @param {String} url 页面地址
     * @param {String} method 提交数据方法（POST/GET/PUT/PROPFIND）
     * @param {String} data 需要提交的字符串数据
     * @param {Function} onReturn 发送结果回调处理（传入：status, message, text）
     * @param {String} user 登录用户（可选，协议用户）
     * @param {String} password 登录密码（可选，协议密码）
     * @returns {XMLHttpRequest} 数据请求对象（返回 null 表示失败）
     */
    this.sendUsingXmlHttp = function(url, method, data, onReturn, user, password) {
        var cover = null;
        var hasCallback = false;
        function callback(status, message, text) {
            if (cover) {
                $W.remove(cover);
                cover = null;
            }
            if (!hasCallback && onReturn) {
                hasCallback = true;
                onReturn.call(_data, status, message, text);
            } else {
                if (status != $G.status.OK) $W.showStatusAlert(status, "", message);
            }
        }
        var http = createHttp.call(_data);
        if (!http) {
            callback($G.status.REQUEST_FAILURE, $G.getText("notSupport"), null);
            return null;
        }
        method = (method && method.length > 0) ? method.toUpperCase() : "GET";
        var async = (onReturn && (typeof onReturn == "function")) ? true : false;
        var login = (user && (typeof user == "string")) ? true : false;
        try {
            if (async) {
                $W.onload(function() {
                    if (hasCallback) return;
                    cover = $W.showLoading(coverId);
                });
                http.onreadystatechange = http.onreadyStatechange = function() {
                    try {
                        if (http.readyState == 4) {
                            var scode = http.status;
                            if (scode == 200 || scode == 0) {
                                callback($G.status.OK, null, http.responseText);
                            } else {
                                callback($G.status.REQUEST_FAILURE, http.statusText, null);
                            }
                        }
                    } catch (ex) {
                        $W.debug($G.getText("dataError"), ex.message, ex.stack);
                        callback($G.status.EXPECTATION, ex.message, null);
                    }
                };
            }
            try {
                if (http.overrideMimeType) {
                    http.overrideMimeType("text/plain; charset=UTF-8");
                }
            } catch (ex2) {
            }
            if (login) {
                http.open(method, url, async, user, password);
            } else {
                http.open(method, url, async);
            }
            var hs = [{
                name: "Content-Type",
                value: "text/xml;charset=UTF-8"
            }, {
                name: "Pragma",
                value: "no-cache"
            }, {
                name: "Expires",
                value: "0"
            }, {
                name: "Cache-Control",
                value: "no-cache, must-revalidate"
            }, {
                name: "Accept",
                value: "text/xml, */*"
            }];
            for (var i = 0; i < hs.length; i++) {
                try {
                    var h = hs[i];
                    http.setRequestHeader(h["name"], h["value"]);
                } catch (e) {
                }
            }
            http.send(data);
            return http;
        } catch (ex2) {
            $W.debug($G.getText("dataError"), ex2.message, ex2.stack);
            callback($G.status.REQUEST_FAILURE, ex2.message, null);
            return null;
        }
    };

    // ---------------------- 发送数据方法 ----------------------

    /**
     * 发送数据到服务端
     * @param {String} cmd 指令值
     * @param {String} ctrl 控制字
     * @param {String} version 数据版本
     * @param {Object} data 需要发送的数据
     * @param {Function} onReturn 服务端响应回调（设置为 null 表示非异步方式，直接返回 $Result 对象表示数据结果；传入：result - $Result 对象）
     * @returns {$Result} 发送结果对象（非 null 对象）
     */
    this.send = function(cmd, ctrl, version, data, onReturn) {
        // Show debug message
        $W.debug($G.getText("sendInfo"), cmd, ctrl, version, data);
        // Create url data
        var async = (onReturn && (typeof onReturn == "function"));
        var sendData = (data) ? new $JSON().toJSONString(data) : "";
        var url = $G.config["protocolUrl"] + "?cmd=" + cmd + "&ctrl=" + ctrl + "&version=" + version;
        try {
            if (async) {
                // Asynchronous mode
                var http = _data.sendUsingXmlHttp(url, "POST", sendData, function(status, message, text) {
                    onReturn.call(_data, createResult.call(_data, cmd, ctrl, version, status, message, text, "A", true));
                });
                var sc = http ? $G.status.OK : $G.status.REQUEST_FAILURE;
                return createResult.call(_data, cmd, ctrl, version, sc, "", sc + "||", "A", false);
            }
            // Synchronous mode
            var http = _data.sendUsingXmlHttp(url, "POST", sendData);
            if (http && http.status == 200 || http.status == 0) return createResult.call(_data, cmd, ctrl, version, $G.status.OK, null, http.responseText, "S", true);
            return createResult.call(_data, cmd, ctrl, version, $G.status.REQUEST_FAILURE, "", null, "S", true);
        } catch (ex) {
            $W.debug($G.getText("dataError"), ex.message, ex.stack);
            return createResult.call(_data, cmd, ctrl, version, $G.status.EXPECTATION, ex.message, null, async ? "A" : "S", true);
        }
    };

    /**
     * 批量发送数据到服务端处理
     * @param {String} cmd 指令值
     * @param {String} ctrl 控制字
     * @param {String} version 数据版本
     * @param {Array} datas 需要发送的数据数组
     * @param {String} action 操作信息字符串
     * @param {Boolean} checkCount 是否检查 result.data.count 的数量信息
     * @param {Function} infoFunc 进度条信息文本获取方法（必须）
     *            <ul>
     *            传入：index
     *            <li>index - 数据序号（从0开始）</li>
     *            返回：void
     *            </ul>
     * @param {Function} onDataReturn 每次数据响应回调（可选）
     *            <ul>
     *            传入：index, succeed, result
     *            <li>index - 数据序号（从0开始）；</li>
     *            <li>succeed - 本次是否成功；</li>
     *            <li>result - 响应结果 $Result 对象；</li>
     *            返回：void
     *            </ul>
     * @param {Function} onComplete 发送执行完成处理（可选）
     *            <ul>
     *            传入：succeedCount, total, result
     *            <li>succeedCount - 成功次数；</li>
     *            <li>total - 数据总数（0 表示已取消执行）；</li>
     *            <li>result - 最后一次传输结果，成功或失败 $Result 对象（取消执行时为 null）；</li>
     *            返回：void
     *            </ul>
     * @param {Boolean} noModule 是否非模态化进度条（可选，默认模态化显示）
     * @param {Boolean} noActionConfirm 是否不需要执行前确认对话框（可选，默认显示操作确认对话框）
     * @param {Boolean} noFailedConfirm 是否不需要传输过程中失败确认对话框（可选，默认显示失败确认对话框）
     * @param {Boolean} noTips 是否不需要结果信息提示（可选，默认显示最终结果提示）
     * @returns void
     */
    this.sendBatch = function(cmd, ctrl, version, datas, action, checkCount, infoFunc, onDataReturn, onComplete, noModule, noActionConfirm, noFailedConfirm, noTips) {
        if (!datas || datas.length == 0) return;
        var index = 0;
        var stopped = false;
        var skipFailed = false;
        var completed = false;
        var succeedCount = 0;
        var total = datas.length;
        var totalInfo = " (" + $G.getText("total", total) + ") ";
        function sendNext(iframe) {
            var sequence = index + 1;
            var data = datas[index];
            var info = infoFunc.call(_data, index);
            var message = info + "...";
            $W.sendToLoadingBar(iframe, message, sequence * 2 - 1);
            _data.send(cmd, ctrl, version, data, function(result) {

                var succeed = checkCount ? (result.succeed() && result.data && result.data.count > 0) : result.succeed();
                if (succeed) {
                    succeedCount++;
                } else {
                    if (checkCount && result.succeed()) result.setStatus($G.status.OTHERS, $G.getText("dataNotExist"));
                }
                if (onDataReturn) onDataReturn.call(_data, index, succeed, result);

                if (stopped) {
                    completed = true;
                    $W.sendToLoadingBar(iframe, null, null, true);
                    if (onComplete) onComplete.call(_data, succeedCount, 0, result);
                    if (!noTips) $W.showTip($G.getText("canceled", action), $G.icon.tipAlarm);
                    return;
                }

                index++;
                $W.sendToLoadingBar(iframe, null, sequence * 2);
                if (index >= total) {
                    completed = true;
                    if (onComplete) onComplete.call(_data, succeedCount, total, result);
                    if (!noTips) {
                        window.setTimeout(function() {
                            if (succeedCount == total) {
                                $W.showTip($G.getText("success", action), $G.icon.tipOk);
                            } else {
                                $W.showTip($G.getText("failedDetail", action), $G.icon.tipAlarm);
                            }
                        }, 1500);
                    }
                    return;
                }

                if (!succeed) {
                    if (result.status == $G.status.REQUEST_FAILURE) {
                        completed = true;
                        $W.sendToLoadingBar(iframe, null, null, true);
                        $W.showStatusAlert(result.status, action, result.message, function(type) {
                            if (onComplete) onComplete.call(_data, succeedCount, total, result);
                        });
                        return;
                    }
                    if (!skipFailed && !noFailedConfirm) {
                        var detail = $W.getGrayNote($G.getText("confirmDetail", (total - sequence), result.message));
                        $W.showAlert($G.getText("confirmFailed", info) + detail, function(type) {
                            switch (type) {
                                case $G.button.no:
                                    completed = true;
                                    $W.sendToLoadingBar(iframe, null, null, true);
                                    if (onComplete) onComplete.call(_data, succeedCount, 0, result);
                                    if (!noTips) $W.showTip($G.getText("canceled", action), $G.icon.tipAlarm);
                                    return;
                                case $G.button.yesToAll:
                                    skipFailed = true;
                                    break;
                                default:
                                    break;
                            }
                            sendNext(iframe);
                        }, $G.icon.question, $G.button.yes | $G.button.yesToAll | $G.button.no);
                        return;
                    }
                }
                sendNext(iframe);
            });
        }
        function startSend() {
            $W.showLoadingBar($G.getText("executing", action + totalInfo), $G.getText("executeSending"), 0, total * 2, function(iframe) {
                sendNext(iframe);
            }, function(iframe, cancelType) {
                if (cancelType == $G.button.cancel) {
                    var msg = $G.getText("confirmCancel") + $W.getGrayNote($G.getText("confirmCancelNote"));
                    $W.showAlert(msg, function(type) {
                        if (type != $G.button.yes) return;
                        stopped = true;
                        if (completed) return;
                        $W.sendToLoadingBar(iframe, $G.getText("canceling"));
                    }, $G.icon.question, $G.button.yes | $G.button.no);
                }
            }, null, noModule);
        }
        if (noActionConfirm) {
            startSend();
        } else {
            $W.showAlert($G.getText("confirm", action) + totalInfo, function(type) {
                if (type != $G.button.yes) {
                    if (onComplete) onComplete.call(_data, 0, 0, null);
                    return;
                }
                startSend();
            }, $G.icon.question, $G.button.yes | $G.button.no, null, false, $G.button.no);
        }
    };

}

// ===================================== TODO: 执行器配置对象规范 =====================================

/**
 * 执行器配置对象规范类
 */
function $ActorProps() {
    /**
     * 执行器语言配置
     */
    this.lang = {
        en_US: {
            title: ""
        }
    };
    /**
     * 执行器文件目录（带结束目录分隔符）
     */
    this.actorPath = "";
    /**
     * 执行器指令
     */
    this.command = "";
    /**
     * 执行器控制版本
     */
    this.version = {
        add: "1",
        deletes: "1",
        edit: "1",
        get: "1",
        count: "1",
        list: "1",
        page: "1",
        update: "1"
    };
    /**
     * 执行器 HTML 文件名称定义（指定后将直接使用 HTML 文件进行操作）
     */
    this.files = {
        add: "xxx-add.html",
        edit: "xxx-edit.html"
    };
    /**
     * 执行器数据主键数组
     */
    this.primaryKeys = [""];
    /**
     * 执行器数据属性字段配置数组
     */
    this.fields = [new $Field()];
}

// -------------------------- 数据属性配置对象 -----------------------------

/**
 * 数据属性配置对象
 */
function $Field() {
    /**
     * 属性字段标识
     */
    this.id = "";
    /**
     * 属性数据最小值（假如是字符串则为长度）
     */
    this.min = 0;
    /**
     * 属性数据最大值（假如是字符串则为长度）
     */
    this.max = 0;
    /**
     * 属性数据类型（对应：$G.dataType.XXXX）
     */
    this.type = "";
    /**
     * 需要限制录入的正则表达式字符串（需排除的匹配项）
     */
    this.limitReg = "";
    /**
     * 显示的字符内容最大字节长度（超过则截取并增加 "..." 省略号）
     */
    this.textLimit = 0;
    /**
     * 是否单行模式
     */
    this.simpleLine = false;
    /**
     * 格式化字符串（对应数据类型，如日期类型："yyyy-mm-dd hh:MM:ss.zzz"；数值类型："0,0.00"；字符串类型："a{0}"；数组类型：","）
     */
    this.format = "";
    /**
     * 是否允许编辑
     */
    this.editable = true;
    /**
     * 新增数据的默认值
     */
    this.defaultValue = null;
    /**
     * 是否为可搜索字段
     */
    this.searchable = true;
    /**
     * 布尔类型数据标识（数据值与本配置值相同时表示真，否则表示假）
     */
    this.booleanMark = "N";
    /**
     * 属性在表单内的位置（值大于 0 时才会在表单内显示）
     */
    this.formIndex = 0;
    /**
     * 属性编辑框在表单内样式
     */
    this.formClass = "";
    /**
     * 属性编辑框宽度（值大于 0 时设置指定宽度）
     */
    this.formFieldWidth = 0;
    /**
     * 属性编辑框高度（值大于 0 时设置指定高度）
     */
    this.formFieldHeight = 0;
    /**
     * 表格内显示的列序号（值大于 0 时才会在表格内显示）
     */
    this.gridColIndex = 0;
    /**
     * 表格表头内的行位置（值大于 1 则显示多行表头）
     */
    this.gridRowIndex = 0;
    /**
     * 表格内合并表头列的数量
     */
    this.gridColSpan = 0;
    /**
     * 表格内合并表头行的数量
     */
    this.gridRowSpan = 0;
    /**
     * 表格列最小宽度（同时用于自动设置宽度量值）
     */
    this.gridColWidth = 0;
    /**
     * 表格单元格样式
     */
    this.gridCellClass = "";
    /**
     * 当前列是否禁止用户排序
     */
    this.gridDisableSort = false;
    /**
     * 目录树显示的列序号（值大于 0 时才会在目录树内显示）
     */
    this.treeIndex = 0;
    /**
     * 是否提示标题字段
     */
    this.treeTitle = false;
    /**
     * 是否节点图标字段
     */
    this.treeIcon = false;
    /**
     * 是否子节点数组字段
     */
    this.treeChildren = false;
    /**
     * 目录树最终子节点标识值（与设置值相同则判定为最终节点）
     */
    this.treeEndMark = "E";
    /**
     * 数据段是否在简单的列表框内显示
     */
    this.inList = false;
    /**
     * 数据段是否在下拉列表框内显示
     */
    this.inSelector = false;
    /**
     * 数据外键链接配置对象（不设置表示无外键）
     */
    this.linked = new $FieldLinked();
}

// -------------------------- 数据外键链接配置对象 -----------------------------

/**
 * 数据外键链接配置对象
 */
function $FieldLinked() {
    /**
     * 外部数据属性标识
     */
    this.id = "";
    /**
     * 需显示的外部数据属性标识
     */
    this.displayId = "";
    /**
     * 下拉窗口宽度（可选，不设置或设置为 0 表示默认自动使用下拉窗口宽度）
     */
    this.width = 0;
    /**
     * 下拉窗口高度（可选，不设置或设置为 0 表示默认自动使用下拉窗口高度）
     */
    this.height = 0;
    /**
     * 输入框是否可编辑
     */
    this.editMode = false;
    /**
     * 是否接受输入的文本内容（只有同时启用编辑模式才有效）
     */
    this.acceptInput = false;
    /**
     * 链接的执行器对象（设置前需在页面内导入执行器 JS 文件）
     */
    this.actor = new $Actor();
    /**
     * 连接的执行器查询条件
     */
    this.where = new $Where();
    /**
     * 执行器是否使用缓存模式（使用后将首先缓存外部数据后再进行显示处理，不使用则实时根据执行器主键从服务端获取数据）
     */
    this.caching = false;
    /**
     * 下拉框数据数组
     */
    this.datas = [];
    /**
     * 下拉框数据集合（键：主键数据（对应 id 属性数据）；值：数据对象）
     */
    this.map = new $HashMap();
}

// ========================================= TODO: 执行器基类定义 =========================================

/**
 * 执行器基类定义
 * @param {$ActorProps} actorProps 执行器配置对象
 * @param {String} fileName 执行器 JS 文件名称
 * @param {String} nameFieldId 名称段标识（可选，默认："name"）
 */
function $BaseActor(actorProps, fileName, nameFieldId) {

    // Declear property type
    if (0 != 0) actorProps = new $ActorProps();
    // Fix parameter
    if (!nameFieldId) nameFieldId = "name";

    /**
     * 执行器配置对象
     */
    this.props = actorProps;
    /**
     * 名称段标识
     */
    this.nameId = nameFieldId;

    // 初始化执行器目录处理
    (function() {
        if (actorProps && !actorProps.actorPath && fileName) {
            var js = document.getElementsByTagName("SCRIPT");
            if (!js) js = document.scripts;
            if (!js) return;
            var file = "/" + fileName;
            for (var i = js.length - 1; i >= 0; i--) {
                var src = js[i].src;
                if (src && src.length > 0 && src.endsWith(file)) {
                    var path = src.substring(0, src.lastIndexOf("/") + 1);
                    $G.setProperty(actorProps, "actorPath", path);
                    break;
                }
            }
        }
    })();

}

// ---------------------- 多国语言方法 -----------------------

/**
 * 获取多国语言文字（不存在则返回 null）
 * @param {String} key 文字标识
 * @returns {String} 文字
 */
$BaseActor.prototype.text = function(key) {
    return this.props.lang[$G.getLanguageKey()][key];
};

/**
 * 获取属性字段对应多国语言文字（返回非 null 字符串）
 * @param {String} fieldId 数据属性名称
 * @returns {String} 文字
 */
$BaseActor.prototype.fieldText = function(fieldId) {
    var txt = this.text("$" + fieldId);
    return txt ? txt : "";
};

/**
 * 根据数据对象获取名字字段（返回非 null 字符串，如："张三"）
 * @param {Object} data 数据对象
 * @returns {String} 文字
 */
$BaseActor.prototype.name = function(data) {
    if (!data) return "";
    var txt = data[this.nameId];
    return txt ? txt : "";
};

/**
 * 获取执行器标题名称（返回非 null 字符串，如："用户"）
 * @returns {String} 文字
 */
$BaseActor.prototype.title = function() {
    var txt = this.text("title");
    return txt ? txt : "";
};

/**
 * 输出多国文字到文档
 * @param {String} key 文字标识
 * @returns void
 */
$BaseActor.prototype.write = function(key) {
    var txt = this.text(key);
    if (txt) document.write(txt);
};

// ---------------------- 执行器辅助方法 -----------------------

/**
 * 获取执行器文件所在目录（含目录分隔符）
 * @returns {String} 目录字符串
 */
$BaseActor.prototype.getActorPath = function() {
    return this.props.actorPath;
};

/**
 * 获取执行器配置数据对象
 * @returns {$ActorProps} 执行器数据配置对象
 */
$BaseActor.prototype.getActorProps = function() {
    return this.props;
};

/**
 * 获取数据属性配置对象（未找到则返回 null）
 * @param {String} fieldId 数据属性标识
 * @returns {$Field} 执行器数据配置对象
 */
$BaseActor.prototype.getField = function(fieldId) {
    var fs = this.props.fields;
    for (var i = 0, c = fs.length; i < c; i++) {
        var field = fs[i];
        if (field.id == fieldId) return field;
    }
    return null;
};

/**
 * 获取数据主键值字符串（无效返回 null）
 * @param {Object} data 数据对象
 * @returns {String} 主键值字符串（每个主键使用分隔符 | 分隔）
 */
$BaseActor.prototype.primaryValueString = function(data) {
    if (!data) return null;
    var pris = this.props.primaryKeys;
    if (!pris || pris.length == 0) return null;
    var values = "";
    for (var i = 0, c = pris.length; i < c; i++) {
        if (i > 0) values += "|";
        values += data[pris[i]];
    }
    if (values.length == 0) return null;
    return values;
};

/**
 * 获取数据主键值数组（对应主键队列，无效返回 null）
 * @param {Object} data 数据对象
 * @returns {Array} 主键值数组
 */
$BaseActor.prototype.primaryValues = function(data) {
    if (!data) return null;
    var pris = this.props.primaryKeys;
    if (!pris || pris.length == 0) return null;
    var values = [];
    for (var i = 0, c = pris.length; i < c; i++) {
        values.push(data[pris[i]]);
    }
    return values;
};

/**
 * 获取执行器数据对象名称提示文本（如："用户 \"管理员\" "）
 * @param {Object} data 数据对象
 * @returns {String} 字符串
 */
$BaseActor.prototype.getNoticeName = function(data) {
    var txt = this.name(data);
    var tt = this.title().toLowerCase();
    if ((txt.length > 0)) return tt + " \"" + $W.getNoticeText(txt) + "\" ";
    return tt;
};

/**
 * 获取执行器名称提示文本（如："用户"）
 * @returns {String} 字符串
 */
$BaseActor.prototype.getNoticeTitle = function() {
    return this.title().toLowerCase();
};

/**
 * 获取操作文本名称（返回非 null 字符串）
 * @param {String} ctrl 操作字符串语言标识（对应全局对象或 ACTOR 语言标识，如：add/edit/get/deletes...）
 * @param {Object} data 数据对象（可选，设置为 null 表示只获取标题）
 * @returns {String} 字符串
 */
$BaseActor.prototype.getControlText = function(ctrl, data) {
    var name = data ? this.getNoticeName(data) : this.getNoticeTitle();
    var action = $G.getText(ctrl, name);
    if (action.length == 0) action = this.text(ctrl);
    if (!action) action = $G.getText("operation");
    return action;
};

/**
 * 检查结果对象内的 count 属性是否具有数量（count > 0）
 * @param {$Result} result 数据传输结果对象
 * @returns {Boolean} 是否存在有数量
 */
$BaseActor.prototype.checkResultCount = function(result) {
    return (result && result.succeed() && result.data && result.data.count > 0);
};

/**
 * 回调执行器结果信息
 * @param {String} ctrl 操作字符串语言标识（对应全局对象或 ACTOR 语言标识，如：add/edit/get...）
 * @param {Object} data 数据对象（可选，设置为 null 表示直接获取标题信息）
 * @param {Function} callback 关闭对话框后回调方法（可选，传入：result - $Result 对象）
 * @param {Boolean} showError 是否显示错误信息（可选，默认：true）
 * @param {$Result} result 当前传输结果 $Result 对象（可选，默认：创建新的数据无效结果对象）
 * @param {Boolean} checkCount 是否检查 result.data.count 的数量信息（可选，默认：false）
 * @param {Boolean} withTips 是否显示成功提示（可选，默认：false）
 * @returns {$Result} 修正后的传输结果 $Result 对象
 */
$BaseActor.prototype.callbackResult = function(ctrl, data, callback, showError, result, checkCount, withTips) {
    if (!result) result = new $Result().init($G.status.DATA_INVALID, "", null);
    var succeed = checkCount ? this.checkResultCount(result) : result.succeed();
    if (succeed) {
        if (withTips) {
            var action = this.getControlText(ctrl, null);
            $W.showTip($G.getText("status" + result.status, action, result.status, ""), $G.icon.tipOk);
        }
        if (callback) callback(result);
    } else {
        if (checkCount && result.succeed()) result.setStatus($G.status.OTHERS, $G.getText("dataNotExist"));
        if (showError == false) {
            if (callback) callback(result);
        } else {
            var action = this.getControlText(ctrl, data);
            $W.showStatusAlert(result.status, action, result.message, function(type) {
                if (callback) callback(result);
            });
        }
    }
    return result;
};

/**
 * 设置链接外部数据配置
 * @param {String} fieldId 内部数据属性标识
 * @param {String} linkedId 外部数据属性标识（外键）
 * @param {String} displayId 显示外部数据值的外部数据属性标识
 * @param {Array} datas 链接数据数组（表现为下拉框显示的数据）
 * @param {Boolean} editMode 输入框是否可编辑（可选，默认：false）
 * @param {Number} width 下拉窗口宽度（可选，不设置或设置为 0 表示默认自动使用下拉窗口宽度）
 * @param {Number} height 下拉窗口高度（可选，不设置或设置为 0 表示默认自动使用下拉窗口高度）
 * @param {Boolean} acceptInput 是否接受输入的文本内容（可选，只有同时启用编辑模式才有效，默认：false）
 * @returns void
 */
$BaseActor.prototype.setLinkedDatas = function(fieldId, linkedId, displayId, datas, editMode, width, height, acceptInput) {
    $Actor.setFieldLinkedDatas(this.props.fields, fieldId, linkedId, displayId, datas, editMode, width, height, acceptInput);
};

// -------------------------- 显示对话框方法 ------------------------

/**
 * 显示增加对话框（只显示增加对话框，数据不提交到后台）
 * @param {Function} onReturn 新增完成回调方法（可选，传入：data - 被增加的数据对象，未增加或增加失败则无数据传入）
 * @param {Function} onSubmit 提交前回调方法（可选，传入：data - 需要提交的数据；返回 false 表示禁止提交）
 * @param {String} className 对话框样式（可选，默认使用：shadow）
 * @param {Number} width 宽度（可选，默认自动处理宽度）
 * @param {Number} height 高度（可选，默认自动处理高度）
 * @param {Number} left 左坐标（可选，默认居中）
 * @param {Number} top 顶坐标（可选，默认居中）
 * @returns {HTMLIFrameElement} 创建的对话框 IFRAME 对象，具备 frameDiv 属性表示外层 DIV
 */
$BaseActor.prototype.showAdd = function(onReturn, onSubmit, className, width, height, left, top) {
    if (this.props.files && this.props.files.add) {
        var url = this.getActorPath() + this.props.files.add;
        return $W.showDialog(url, null, true, onReturn, onSubmit, null, className, width, height, left, top);
    }
    var actor = this;
    return $W.showAdd(this.title(), this.props.fields, function(fieldId) {
        return actor.fieldText(fieldId);
    }, function(iframe, data, addCallback) {
        if (onSubmit && onSubmit.call(actor, data) === false) {
            addCallback.call(actor, false, data);
        } else {
            addCallback.call(actor, true, data);
        }
    }, onReturn, className, width, height, left, top);
};

/**
 * 显示编辑对话框（只显示编辑对话框，数据不提交到后台）
 * @param {Object} data 需要编辑的数据（必须）
 * @param {Function} onReturn 编辑完成回调方法（可选，传入：data - 被编辑的数据对象，未编辑或编辑失败则不存在传入数据）
 * @param {Function} onSubmit 提交前回调方法（可选，传入：data - 需要提交的数据；返回 false 表示禁止提交）
 * @param {String} className 对话框样式（可选，默认使用：shadow）
 * @param {Number} width 宽度（可选，默认自动处理宽度）
 * @param {Number} height 高度（可选，默认自动处理高度）
 * @param {Number} left 左坐标（可选，默认居中）
 * @param {Number} top 顶坐标（可选，默认居中）
 * @returns {HTMLIFrameElement} 创建的对话框 IFRAME 对象，具备 frameDiv 属性表示外层 DIV
 */
$BaseActor.prototype.showEdit = function(data, onReturn, onSubmit, className, width, height, left, top) {
    if (this.props.files && this.props.files.edit) {
        var url = this.getActorPath() + this.props.files.edit;
        return $W.showDialog(url, data, true, onReturn, onSubmit, null, className, width, height, left, top);
    }
    var actor = this;
    return $W.showEdit(this.title(), data, this.props.fields, function(fieldId) {
        return actor.fieldText(fieldId);
    }, function(iframe, newData, editCallback) {
        if (onSubmit && onSubmit.call(actor, newData) === false) {
            editCallback.call(actor, false, newData);
        } else {
            editCallback.call(actor, true, newData);
        }
    }, onReturn, className, width, height, left, top);
};

/**
 * 显示删除对话框（只显示删除对话框，数据不提交到后台）
 * @param {Object} data 需要删除的数据（必须）
 * @param {Function} callback 对话框确认回调方法（传入：toDelete - 是否需要删除；true - 已确认需要删除数据；false - 不需要删除数据）
 * @returns {HTMLIFrameElement} 创建的对话框 IFRAME 对象，具备 frameDiv 属性表示外层 DIV
 */
$BaseActor.prototype.showDelete = function(data, callback) {
    var action = $G.getText("deletes", this.getNoticeName(data));
    $W.showAlert($G.getText("confirm", action), function(type) {
        if (!callback) return;
        if (type == $G.button.yes) {
            callback(true);
        } else {
            callback(false);
        }
    }, $G.icon.question, $G.button.yes | $G.button.no, null, false, $G.button.no);
};

/**
 * 发送数据到后台处理（返回发送结果 $Result 对象：result）
 * @param {String} cmd 指令值
 * @param {String} ctrl 控制字
 * @param {String} version 数据版本
 * @param {Object} data 需要发送的数据
 * @param {Function} callback 服务端响应回调（设置为 null 表示非异步模式；传入：result - $Result 对象）
 * @param {Boolean} checkCount 是否检查 result.data.count 的数量信息（可选，默认：false）
 * @param {String} coverId 发送数据是需要显示加载进度遮罩的元素标识（可选，默认不进行遮罩）
 * @param {Boolean} showError 是否显示错误信息（可选，默认：true）
 * @param {Boolean} withTips 是否显示成功提示（可选，默认：false）
 * @returns {$Result} 发送结果对象（非 null 对象）
 */
$BaseActor.prototype.send = function(cmd, ctrl, version, data, callback, checkCount, coverId, showError, withTips) {
    if (callback) {
        var actor = this;
        return new $Data(coverId).send(cmd, ctrl, version, data, function(result) {
            actor.callbackResult(ctrl, data, callback, showError, result, checkCount, withTips);
        });
    }
    var result = new $Data(coverId).send(cmd, ctrl, version, data);
    return this.callbackResult(ctrl, data, null, showError, result, checkCount, withTips);
};

// ========================================= TODO: 增删改查执行器基类定义 =========================================

/**
 * 增删改查执行器基类定义
 * @param {$ActorProps} actorProps 执行器配置对象
 * @param {String} fileName 执行器 JS 文件名称
 * @param {String} nameFieldId 名称段标识（可选，默认："name"）
 */
function $Actor(actorProps, fileName, nameFieldId) {

    // Declear property type
    if (0 != 0) actorProps = new $ActorProps();

    /**
     * 执行器配置对象
     */
    this.props = actorProps;
    /**
     * 名称段标识
     */
    this.nameId = "name";

    // 继承属性处理
    $BaseActor.call(this, actorProps, fileName, nameFieldId);
}

// 继承执行器基类操作
$Actor.prototype = new $BaseActor();

// -------------------------- 显示对话框方法 ------------------------

/**
 * 显示增加对话框（数据将被提交到后台）
 * @param {Function} onReturn 新增完成回调方法（可选，传入：data - 被增加的数据对象，未增加或增加失败则无数据传入）
 * @param {Function} onSubmit 提交前回调方法（可选，传入：data - 需要提交的数据；返回 false 表示禁止提交）
 * @param {String} className 对话框样式（可选，默认使用：shadow）
 * @param {Number} width 宽度（可选，默认自动处理宽度）
 * @param {Number} height 高度（可选，默认自动处理高度）
 * @param {Number} left 左坐标（可选，默认居中）
 * @param {Number} top 顶坐标（可选，默认居中）
 * @returns {HTMLIFrameElement} 创建的对话框 IFRAME 对象，具备 frameDiv 属性表示外层 DIV
 */
$Actor.prototype.showAdd = function(onReturn, onSubmit, className, width, height, left, top) {
    if (this.props.files && this.props.files.add) {
        var url = this.getActorPath() + this.props.files.add;
        return $W.showDialog(url, null, true, onReturn, onSubmit, null, className, width, height, left, top);
    }
    var actor = this;
    return $W.showAdd(this.title(), this.props.fields, function(fieldId) {
        return actor.fieldText(fieldId);
    }, function(iframe, data, addCallback) {
        if (onSubmit && onSubmit.call(actor, data) === false) {
            addCallback.call(actor, false, data);
        } else {
            actor.add(data, function(result) {
                addCallback.call(actor, result.succeed(), result.data);
            }, document);
        }
    }, onReturn, className, width, height, left, top);
};

/**
 * 显示编辑对话框（重新获取最新数据进行编辑，并把编辑结果提交到后台）
 * @param {Object} data 需要编辑的数据（必须）
 * @param {Function} onReturn 编辑完成回调方法（可选，传入：data - 被编辑的数据对象，未编辑或编辑失败则不存在传入数据）
 * @param {Function} onSubmit 提交前回调方法（可选，传入：data - 需要提交的数据；返回 false 表示禁止提交）
 * @param {String} className 对话框样式（可选，默认使用：shadow）
 * @param {Number} width 宽度（可选，默认自动处理宽度）
 * @param {Number} height 高度（可选，默认自动处理高度）
 * @param {Number} left 左坐标（可选，默认居中）
 * @param {Number} top 顶坐标（可选，默认居中）
 * @returns {HTMLIFrameElement} 创建的对话框 IFRAME 对象，具备 frameDiv 属性表示外层 DIV
 */
$Actor.prototype.showEdit = function(data, onReturn, onSubmit, className, width, height, left, top) {
    var pkValues = this.primaryValues(data);
    if (pkValues && pkValues.length > 0) {
        var ret = this.get(pkValues, null, null, false, false);
        if (ret.succeed() && ret.data) data = ret.data;
    }
    if (this.props.files && this.props.files.edit) {
        var url = this.getActorPath() + this.props.files.edit;
        return $W.showDialog(url, data, true, onReturn, onSubmit, null, className, width, height, left, top);
    }
    var actor = this;
    return $W.showEdit(this.title(), data, this.props.fields, function(fieldId) {
        return actor.fieldText(fieldId);
    }, function(iframe, newData, editCallback) {
        if (onSubmit && onSubmit.call(actor, newData) === false) {
            editCallback.call(actor, false, newData);
        } else {
            actor.edit(newData, function(result) {
                editCallback.call(actor, result.succeed(), result.data);
            }, document);
        }
    }, onReturn, className, width, height, left, top);
};

/**
 * 显示删除对话框（数据将被提交到后台，成功时回调 result.data: {count: Number - 受影响数据条数}）
 * @param {Object} data 需要删除的数据（必须）
 * @param {Function} callback 服务端响应回调（传入：deleted - 是否已经删除成功，取消删除传入 false）
 * @param {String} coverId 发送数据是需要显示加载进度遮罩的元素标识（可选，默认不进行遮罩）
 * @param {Boolean} showError 是否显示错误信息（可选，默认：true）
 * @param {Boolean} withTips 是否显示成功提示（可选，默认：true）
 * @returns {HTMLIFrameElement} 创建的对话框 IFRAME 对象，具备 frameDiv 属性表示外层 DIV
 */
$Actor.prototype.showDelete = function(data, callback, coverId, showError, withTips) {
    var actor = this;
    var action = $G.getText("deletes", this.getNoticeName(data));
    return $W.showAlert($G.getText("confirm", action), function(type) {
        if (type == $G.button.yes) {
            actor.deleteBean(data, function(result) {
                if (callback) callback.call(actor, result.succeed());
            }, coverId, showError, withTips);
        } else {
            if (callback) callback.call(actor, false);
        }
    }, $G.icon.question, $G.button.yes | $G.button.no, null, false, $G.button.no);
};

// ----------------------------- TODO: 增删改查方法 -----------------------------

/**
 * 增加数据处理（成功时回调的 result.data: Object - 被增加的数据对象）
 * @param {Object} data 数据对象
 * @param {Function} callback 服务端响应回调（设置为 null 表示非异步模式；传入：result - $Result 对象）
 * @param {String} coverId 发送数据是需要显示加载进度遮罩的元素标识（可选，默认不进行遮罩）
 * @param {Boolean} showError 是否显示错误信息（可选，默认：true）
 * @param {Boolean} withTips 是否显示成功提示（可选，默认：true）
 * @returns {$Result} 发送结果对象（非 null 对象）
 */
$Actor.prototype.add = function(data, callback, coverId, showError, withTips) {
    if (data) return this.send(this.props.command, "add", this.props.version.add, data, callback, false, coverId, showError, (withTips != false));
    return this.callbackResult("add", data, callback, showError, null, false, (withTips != false));
};

/**
 * 删除数据处理（成功时回调的 result.data: {count: Number - 受影响数据条数}）
 * @param {Object} data 数据对象
 * @param {Function} callback 服务端响应回调（设置为 null 表示非异步模式；传入：result - $Result 非 null 对象）
 * @param {String} coverId 发送数据是需要显示加载进度遮罩的元素标识（可选，默认不进行遮罩）
 * @param {Boolean} showError 是否显示错误信息（可选，默认：true）
 * @param {Boolean} withTips 是否显示成功提示（可选，默认：true）
 * @returns {$Result} 发送结果对象（非 null 对象）
 */
$Actor.prototype.deleteBean = function(data, callback, coverId, showError, withTips) {
    var where = this.getPrimaryWhere(data);
    if (where) return this.send(this.props.command, "deletes", this.props.version.deletes, where, callback, true, coverId, showError, (withTips != false));
    return this.callbackResult("deletes", data, callback, showError, null, true, (withTips != false));
};

/**
 * 删除多个数据处理（存在多次回调，成功时回调的：result.data: {count: Number - 受影响数据条数}）
 * @param {Array} datas 数据对象数组
 * @param {Function} onDataReturn 每次数据响应回调（可选）
 *            <ul>
 *            传入：succeed, data, result
 *            <li>succeed - 本次是否成功；</li>
 *            <li>data - 发送的数据对象；</li>
 *            <li>result - 响应结果 $Result 对象；</li>
 *            返回：void
 *            </ul>
 * @param {Function} onComplete 发送执行完成处理（可选）
 *            <ul>
 *            传入：succeedCount, total, result
 *            <li>succeedCount - 成功次数；</li>
 *            <li>total - 数据总数（0 表示已取消执行）；</li>
 *            <li>result - 最后一次传输结果，成功或失败 $Result 对象（取消执行时为 null）；</li>
 *            返回：void
 *            </ul>
 * @param {Boolean} moduless 是否非模态化进度条（可选，默认：true）
 * @param {Boolean} withTips 是否显示成功提示（可选，默认：true）
 * @returns void
 */
$Actor.prototype.deleteBeans = function(datas, onDataReturn, onComplete, moduless, withTips) {
    if (datas && datas.length > 0) {
        var actor = this;
        var wheres = $Actor.getWhereBatch(datas, this.props.primaryKeys);
        var action = $G.getText("deleteBatch", this.getNoticeTitle());
        new $Data().sendBatch(this.props.command, "deletes", this.props.version.deletes, wheres, action, true, function(index) {
            return $G.getText("deletes", actor.getNoticeName(datas[index]));
        }, function(index, succeed, result) {
            if (onDataReturn) onDataReturn(succeed, datas[index], result);
        }, onComplete, (moduless == false), (withTips == false));
    } else {
        this.callbackResult("deletes", null, function(result) {
            if (onComplete) onComplete(0, 0, result);
        });
    }
};

/**
 * 按条件删除所有数据处理（成功时回调的 result.data: {count: Number - 受影响数据条数}）
 * @param {Function} callback 服务端响应回调（设置为 null 表示非异步模式；传入：result - $Result 对象，取消时为 null）
 * @param {$Where} where 删除条件（可选，不设置则删除所有数据）
 * @param {Number} count 需删除的数据总数（可选，设置后将提示总数信息）
 * @param {String} coverId 发送数据是需要显示加载进度遮罩的元素标识（可选，默认不进行遮罩）
 * @param {Boolean} showConfirm 是否显示确认提示信息（可选，默认：true，需要确认信息时总是直接返回成功结果）
 * @param {Boolean} showError 是否显示错误信息（可选，默认：true）
 * @param {Boolean} withTips 是否显示成功提示（可选，默认：true）
 * @returns {$Result} 发送结果对象（非 null 对象）
 */
$Actor.prototype.deleteAll = function(callback, where, count, coverId, showConfirm, showError, withTips) {
    var result = null;
    where = (!where) ? new $Where() : where.cloneWhere(false);
    if (callback) {
        var actor = this;
        if (showConfirm != false) {
            var totalInfo = count > 0 ? " (" + $G.getText("total", count) + ") " : "";
            var action = $G.getText("deleteAll", this.getNoticeTitle());
            $W.showAlert($G.getText("confirm", action) + totalInfo + $W.getAlarmNote($G.getText("deleteNote")), function(type) {
                if (type != $G.button.yes) {
                    if (callback) callback.call(actor, null);
                    return;
                }
                new $Data(coverId).send(actor.props.command, "deletes", actor.props.version.deletes, where, function(res) {
                    actor.callbackResult("deletes", null, callback, showError, res, true, (withTips != false));
                });
            }, $G.icon.question, $G.button.yes | $G.button.no, null, false, $G.button.no);
            return new $Result().init($G.status.OK);
        }
        return new $Data(coverId).send(this.props.command, "deletes", this.props.version.deletes, where, function(res) {
            actor.callbackResult("deletes", null, callback, showError, res, true, (withTips != false));
        });
    }
    result = new $Data(coverId).send(this.props.command, "deletes", this.props.version.deletes, where);
    return this.callbackResult("deletes", null, callback, showError, result, true, (withTips != false));
};

/**
 * 编辑数据处理（成功时回调的 result.data: Object - 编辑后的数据对象）
 * @param {Object} data 数据对象
 * @param {Function} callback 服务端响应回调（设置为 null 表示非异步模式；传入：result - $Result 对象）
 * @param {String} coverId 发送数据是需要显示加载进度遮罩的元素标识（可选，默认不进行遮罩）
 * @param {Boolean} showError 是否显示错误信息（可选，默认：true）
 * @param {Boolean} withTips 是否显示成功提示（可选，默认：true）
 * @returns {$Result} 发送结果对象（非 null 对象）
 */
$Actor.prototype.edit = function(data, callback, coverId, showError, withTips) {
    if (data) return this.send(this.props.command, "edit", this.props.version.edit, data, callback, false, coverId, showError, (withTips != false));
    return this.callbackResult("edit", data, callback, showError, null, false, (withTips != false));
};

/**
 * 获取数据处理（成功时回调的 result.data: Object - 获取到的数据对象）
 * @param {Array} pkValues 数据主键数据数组（按 props 配置文件内定义的主键顺序设置）
 * @param {Function} callback 服务端响应回调（设置为 null 表示非异步模式；传入：result - $Result 对象）
 * @param {String} coverId 发送数据是需要显示加载进度遮罩的元素标识（可选，默认不进行遮罩）
 * @param {Boolean} showError 是否显示错误信息（可选，默认：true）
 * @param {Boolean} withTips 是否显示成功提示（可选，默认：false）
 * @returns {$Result} 发送结果对象（非 null 对象）
 */
$Actor.prototype.get = function(pkValues, callback, coverId, showError, withTips) {
    var pks = this.props.primaryKeys;
    if (pkValues && pkValues.length == pks.length) {
        var data = new $Where();
        for (var i = 0, c = pkValues.length; i < c; i++) {
            data.equals(pks[i], pkValues[i]);
        }
        if (data) return this.send(this.props.command, "get", this.props.version.get, data, callback, false, coverId, showError, withTips);
    }
    return this.callbackResult("get", null, callback, showError, null, false, withTips);
};

/**
 * 按条件获取数据总数（成功时回调的 result.data: {count: Number - 数据总数}）
 * @param {Function} callback 服务端响应回调（设置为 null 表示非异步模式；传入：result - $Result 对象）
 * @param {$Where} where 查询条件（可选）
 * @param {String} coverId 发送数据是需要显示加载进度遮罩的元素标识（可选，默认不进行遮罩）
 * @param {Boolean} showError 是否显示错误信息（可选，默认：true）
 * @param {Boolean} withTips 是否显示成功提示（可选，默认：false）
 * @returns {$Result} 发送结果对象（非 null 对象）
 */
$Actor.prototype.getCount = function(callback, where, coverId, showError, withTips) {
    if (!where) where = new $Where();
    return this.send(this.props.command, "count", this.props.version.count, where, callback, false, coverId, showError, withTips);
};

/**
 * 按条件获取数据列表（成功时回调的 result.data: Array - 数据对象数组}）
 * @param {Function} callback 服务端响应回调（设置为 null 表示非异步模式；传入：result - $Result 对象）
 * @param {$Where} where 查询条件（可选）
 * @param {String} coverId 发送数据是需要显示加载进度遮罩的元素标识（可选，默认不进行遮罩）
 * @param {Boolean} showError 是否显示错误信息（可选，默认：true）
 * @param {Boolean} withTips 是否显示成功提示（可选，默认：false）
 * @returns {$Result} 发送结果对象（非 null 对象）
 */
$Actor.prototype.getList = function(callback, where, coverId, showError, withTips) {
    if (!where) where = new $Where();
    return this.send(this.props.command, "list", this.props.version.list, where, callback, false, coverId, showError, withTips);
};

/**
 * 按分页获取数据列表（成功时回调的 result.data: {count: Number - 数据总数, datas: Array - 数据对象数组}）
 * @param {Number} page 当前页
 * @param {Number} pageSize 每页显示数据条数
 * @param {Boolean} needCount 是否需要统计数据总数
 * @param {Function} callback 服务端响应回调（设置为 null 表示非异步模式；传入：result - $Result 对象）
 * @param {$Where} where 查询条件（可选）
 * @param {String} coverId 发送数据是需要显示加载进度遮罩的元素标识（可选，默认不进行遮罩）
 * @param {Boolean} showError 是否显示错误信息（可选，默认：true）
 * @param {Boolean} withTips 是否显示成功提示（可选，默认：false）
 * @returns {$Result} 发送结果对象（非 null 对象）
 */
$Actor.prototype.getPage = function(page, pageSize, needCount, callback, where, coverId, showError, withTips) {
    if (!where) where = new $Where();
    var rcall = null;
    // Count mode
    if (needCount) {
        var actor = this;
        if (callback) {
            // Async mode
            rcall = function(crs) {
                if (crs.succeed()) {
                    if (crs.data.count > 0) {
                        actor.send(actor.props.command, "page", actor.props.version.page, {
                            page: page,
                            pageSize: pageSize,
                            wheres: where.wheres,
                            orders: where.orders
                        }, function(prs) {
                            if (prs.succeed()) crs.data.datas = prs.data;
                            callback(crs);
                        }, false, coverId, showError, withTips);
                    } else {
                        crs.data.datas = [];
                        callback(crs);
                    }
                } else {
                    callback(crs);
                }
            };
        }
        var crsn = this.getCount(rcall, where, coverId, showError, withTips);
        if (!rcall && crsn.succeed()) {
            // Sync mode
            var crsc = crsn.data.count;
            if (crsc > 0) {
                crsn = this.send(this.props.command, "page", this.props.version.page, {
                    page: page,
                    pageSize: pageSize,
                    wheres: where.wheres,
                    orders: where.orders
                }, null, false, coverId, showError, withTips);
                if (crsn.succeed()) {
                    crsn.data = {
                        count: crsc,
                        datas: crsn.data
                    };
                }
            } else {
                crsn.data.datas = [];
            }
        }
        return crsn;
    }
    // No count mode
    if (callback) {
        // Async mode
        rcall = function(pprs) {
            if (pprs.succeed()) {
                pprs.data = {
                    count: -1,
                    datas: pprs.data
                };
                callback(pprs);
            } else {
                callback(pprs);
            }
        };
    }
    var nprs = this.send(this.props.command, "page", this.props.version.page, {
        page: page,
        pageSize: pageSize,
        wheres: where.wheres,
        orders: where.orders
    }, rcall, false, coverId, showError, withTips);
    if (!rcall && nprs.succeed()) {
        // Sync mode
        nprs.data = {
            count: -1,
            datas: nprs.data
        };
    }
    return nprs;
};

/**
 * 按条件更新数据处理（成功时回调的 result.data: {count: Number - 受影响数据条数}）
 * @param {Function} callback 服务端响应回调（设置为 null 表示非异步模式；传入：result - $Result 对象）
 * @param {$Update} update 更新数据设置对象
 * @param {$Where} where 更新条件
 * @param {String} coverId 发送数据是需要显示加载进度遮罩的元素标识（可选，默认不进行遮罩）
 * @param {Boolean} showError 是否显示错误信息（可选，默认：true）
 * @param {Boolean} withTips 是否显示成功提示（可选，默认：false）
 * @returns {$Result} 发送结果对象（非 null 对象）
 */
$Actor.prototype.update = function(callback, update, where, coverId, showError, withTips) {
    if (update && where) {
        var data = {
            updates: update.updates,
            wheres: where.wheres
        };
        return this.send(this.props.command, "update", this.props.version.update, data, callback, true, coverId, showError, withTips);
    }
    return this.callbackResult("update", null, callback, showError, null, true, withTips);
};

// ---------------------- 执行器辅助方法 -----------------------

/**
 * 获取数据主键查询条件（对应主键队列，无效返回 null）
 * @param {Object} data 数据对象
 * @returns {$Where} 查询条件
 */
$Actor.prototype.getPrimaryWhere = function(data) {
    return $Actor.getWhere(data, this.props.primaryKeys);
};

/**
 * 设置链接外部执行器配置
 * @param {String} fieldId 内部数据属性标识（必须）
 * @param {String} linkedId 外部数据属性标识（外键，必须）
 * @param {String} displayId 显示外部数据值的外部数据属性标识（必须）
 * @param {$Actor} actor 外部数据执行器对象（必须）
 * @param {$Where} where 查询条件（可选）
 * @param {Boolean} caching 是否使用缓存模式（可选，使用后将立即缓存外部数据，不使用则实时根据主键从服务端获取数据）
 * @param {Boolean} editMode 输入框是否可编辑（可选，默认：false）
 * @param {Number} width 下拉窗口宽度（可选，不设置或设置为 0 表示默认自动使用下拉窗口宽度）
 * @param {Number} height 下拉窗口高度（可选，不设置或设置为 0 表示默认自动使用下拉窗口高度）
 * @param {Boolean} acceptInput 是否接受输入的文本内容（可选，只有同时启用编辑模式才有效，默认：false）
 * @returns void
 */
$Actor.prototype.setLinkedActor = function(fieldId, linkedId, displayId, actor, where, caching, editMode, width, height, acceptInput) {
    $Actor.setFieldLinkedActor(this.props.fields, fieldId, linkedId, displayId, actor, where, caching, editMode, width, height, acceptInput);
};

// -------------------------- 执行器静态方法 ------------------------

/**
 * 获取查询数据条件（使用等于运算，无效则返回 null）
 * @param {Object} data 数据对象
 * @param {Array} filedIds 数据属性名称数组
 * @returns {$Where} 查询条件对象
 */
$Actor.getWhere = function(data, filedIds) {
    if (!data || !filedIds || filedIds.length == 0) return null;
    var where = new $Where();
    for (var i = 0, c = filedIds.length; i < c; i++) {
        var fieldId = filedIds[i];
        where.equals(fieldId, data[fieldId]);
    }
    return where;
};

/**
 * 获取批量查询数据条件（使用等于运算，无效则返回 null）
 * @param {Array} datas 数据对象数组
 * @param {Array} filedIds 数据属性名称数组
 * @returns {Array} 查询条件对象数组
 */
$Actor.getWhereBatch = function(datas, filedIds) {
    if (!datas || datas.length == 0 || !filedIds || filedIds.length == 0) return null;
    var wheres = [], c = filedIds.length;
    for (var n = 0, m = datas.length; n < m; n++) {
        var data = datas[n];
        var where = new $Where();
        for (var i = 0; i < c; i++) {
            var fieldId = filedIds[i];
            where.equals(fieldId, data[fieldId]);
        }
        wheres.push(where);
    }
    return wheres;
};

/**
 * 获取连接的外键数据（无效则返回 null）
 * @param {Object} data 当前数据对象
 * @param {$Field} field 数据配置对象
 * @param {Object} linkedValue 当前数据外键真实值（可选，设置后将忽略 data 参数）
 * @returns {Object} 获取到的外键数据对象
 */
$Actor.getLinkedData = function(data, field, linkedValue) {
    if ((!data && !linkedValue) || !field) return null;
    var linked = field.linked;
    if (!linked) return null;
    var map = linked.map;
    if (!linkedValue) linkedValue = data[field.id];
    if (linked.actor && !linked.caching) {
        var value = map.get(linkedValue);
        if (!value) {
            var actor = linked.actor;
            var where = new $Where().equals(linked.id, linkedValue);
            var result = new $Data().send(actor.props.command, "get", actor.props.version.get, where);
            if (result.succeed() && result.data) {
                value = result.data;
                map.put(linkedValue, value);
            }
        }
        return value;
    }
    return map.get(linkedValue);
};

/**
 * 设置链接外部执行器配置
 * @param {Array} fields 执行器数据属性字段配置数组
 * @param {String} fieldId 内部数据属性标识（必须）
 * @param {String} linkedId 外部数据属性标识（外键，必须）
 * @param {String} displayId 显示外部数据值的外部数据属性标识（必须）
 * @param {$Actor} actor 外部数据执行器对象（必须）
 * @param {$Where} where 查询条件（可选）
 * @param {Boolean} caching 是否使用缓存模式（可选，使用后将立即缓存外部数据，不使用则实时根据主键从服务端获取数据）
 * @param {Boolean} editMode 输入框是否可编辑（可选，默认：false）
 * @param {Number} width 下拉窗口宽度（可选，不设置或设置为 0 表示默认自动使用下拉窗口宽度）
 * @param {Number} height 下拉窗口高度（可选，不设置或设置为 0 表示默认自动使用下拉窗口高度）
 * @param {Boolean} acceptInput 是否接受输入的文本内容（可选，只有同时启用编辑模式才有效，默认：false）
 * @returns void
 */
$Actor.setFieldLinkedActor = function(fields, fieldId, linkedId, displayId, actor, where, caching, editMode, width, height, acceptInput) {
    if (!fields) return;
    for (var i = 0, c = fields.length; i < c; i++) {
        var field = fields[i];
        if (field.id == fieldId) {
            var datas = [];
            var map = new $HashMap();
            if (!where) where = new $Where();
            if (caching) {
                var result = actor.getList(null, where, null, false, false);
                if (result.succeed() && result.data) {
                    datas = result.data;
                    for (var i = 0, c = datas.length; i < c; i++) {
                        var obj = datas[i];
                        map.put(obj[linkedId], obj);
                    }
                }
            }
            field.linked = {
                id: linkedId,
                displayId: displayId,
                editMode: editMode,
                actor: actor,
                where: where,
                caching: caching,
                datas: datas,
                map: map,
                width: (width && width > 0 ? width : 0),
                height: (height && height > 0 ? height : 0),
                acceptInput: acceptInput
            };
            return;
        }
    }
};

/**
 * 设置链接外部数据配置
 * @param {Array} fields 执行器数据属性字段配置数组
 * @param {String} fieldId 内部数据属性标识
 * @param {String} linkedId 外部数据属性标识（外键）
 * @param {String} displayId 显示外部数据值的外部数据属性标识
 * @param {Array} datas 链接数据数组（表现为下拉框显示的数据）
 * @param {Boolean} editMode 输入框是否可编辑（可选，默认：false）
 * @param {Number} width 下拉窗口宽度（可选，不设置或设置为 0 表示默认自动使用下拉窗口宽度）
 * @param {Number} height 下拉窗口高度（可选，不设置或设置为 0 表示默认自动使用下拉窗口高度）
 * @param {Boolean} acceptInput 是否接受输入的文本内容（可选，只有同时启用编辑模式才有效，默认：false）
 * @returns void
 */
$Actor.setFieldLinkedDatas = function(fields, fieldId, linkedId, displayId, datas, editMode, width, height, acceptInput) {
    if (!fields) return;
    for (var i = 0, c = fields.length; i < c; i++) {
        var field = fields[i];
        if (field.id == fieldId) {
            var map = new $HashMap();
            for (var i = 0, c = datas.length; i < c; i++) {
                var obj = datas[i];
                map.put(obj[linkedId], obj);
            }
            field.linked = {
                id: linkedId,
                displayId: displayId,
                editMode: editMode,
                datas: datas,
                map: map,
                width: (width && width > 0 ? width : 0),
                height: (height && height > 0 ? height : 0),
                acceptInput: acceptInput
            };
            return;
        }
    }
};

// ----------------------------------- 数据条件类 -------------------------------------

/**
 * 执行器数据更新数据设置对象
 */
function $Update() {

    /**
     * 条件数组
     */
    this.updates = [];

    /**
     * 设置需要更新的数据值
     * @param {String} fieldId 对象属性名称
     * @param {Object} value 需要设置的值
     * @returns {$Update} 条件对象本身
     */
    this.set = function(fieldId, value) {
        this.updates.push({
            k: fieldId,
            v: value
        });
        return this;
    };

}

/**
 * 执行器条件对象
 */
function $Where() {

    /**
     * 条件数组
     */
    this.wheres = [];
    /**
     * 排序数组
     */
    this.orders = [];
    /**
     * 最多排序个数
     */
    var _maxOrders = 2;

    // ------------------------ 查询条件处理 ---------------------------

    /**
     * 增加大于条件值
     * @param {String} fieldId 对象属性名称
     * @param {Object} value 需要大于的数值
     * @returns {$Where} 条件对象本身
     */
    this.greater = function(fieldId, value) {
        this.wheres.push({
            k: fieldId,
            o: ">",
            v: value
        });
        return this;
    };

    /**
     * 增加大于或等于条件值
     * @param {String} fieldId 对象属性名称
     * @param {Object} value 需要大于或等于的数值
     * @returns {$Where} 条件对象本身
     */
    this.greaterEquals = function(fieldId, value) {
        this.wheres.push({
            k: fieldId,
            o: ">=",
            v: value
        });
        return this;
    };

    /**
     * 增加等于条件值
     * @param {String} fieldId 对象属性名称
     * @param {Object} value 需要等于的数值
     * @returns {$Where} 条件对象本身
     */
    this.equals = function(fieldId, value) {
        this.wheres.push({
            k: fieldId,
            o: "=",
            v: value
        });
        return this;
    };

    /**
     * 增加小于条件值
     * @param {String} fieldId 对象属性名称
     * @param {Object} value 需要小于的数值
     * @returns {$Where} 条件对象本身
     */
    this.less = function(fieldId, value) {
        this.wheres.push({
            k: fieldId,
            o: "<",
            v: value
        });
        return this;
    };

    /**
     * 增加小于或等于条件值
     * @param {String} fieldId 对象属性名称
     * @param {Object} value 需要小于或等于的数值
     * @returns {$Where} 条件对象本身
     */
    this.lessEquals = function(fieldId, value) {
        this.wheres.push({
            k: fieldId,
            o: "<=",
            v: value
        });
        return this;
    };

    /**
     * 增加不等于条件值
     * @param {String} fieldId 对象属性名称
     * @param {Object} value 需要不等于的数值
     * @returns {$Where} 条件对象本身
     */
    this.notEquals = function(fieldId, value) {
        this.wheres.push({
            k: fieldId,
            o: "<>",
            v: value
        });
        return this;
    };

    /**
     * 增加相似条件值（即 sql like 条件，如：'%A%'）
     * @param {String} fieldId 对象属性名称
     * @param {Object} value 需要相似的数值
     * @returns {$Where} 条件对象本身
     */
    this.like = function(fieldId, value) {
        this.wheres.push({
            k: fieldId,
            o: "like",
            v: value
        });
        return this;
    };

    /**
     * 移除查询条件处理
     * @param {String} fieldId 对象属性名称
     * @returns {$Where} 条件对象本身
     */
    this.removeWhere = function(fieldId) {
        if (!fieldId) return this;
        for (var i = 0, c = this.wheres.length; i < c; i++) {
            var data = this.wheres[i];
            if (data["k"] == fieldId) {
                this.wheres.splice(i, 1);
                i--;
                c--;
            }
        }
        return this;
    };

    /**
     * 清空查询条件处理
     * @returns {$Where} 对象本身
     */
    this.clearWhere = function() {
        this.wheres = [];
        return this;
    };

    // ------------------------ 排序条件处理 ---------------------------

    /**
     * 设置最大排序字段个数（默认：2，设置为 0 表示不限制，设置大于 0 表示超出个数后将剔除最后一个排序条件）
     * @param {Number} max 最多排序个数
     * @returns {$Where} 对象本身
     */
    this.setMaxOrders = function(max) {
        _maxOrders = max;
        return this;
    };

    /**
     * 增加排序条件
     * @param {String} fieldId 对象属性名称
     * @param {Boolean} isAsc 是否顺序排列
     * @param {Number} index 排序条件在数组内的插入位置（可选，默认增加到数组最后位置）
     * @returns {$Where} 对象本身
     */
    this.orderBy = function(fieldId, isAsc, index) {
        if (!fieldId) return this;
        this.removeOrder(fieldId);
        var c = this.orders.length;
        if (_maxOrders > 0 && c >= _maxOrders) {
            var deletecount = _maxOrders - c + 1;
            this.orders.splice(c - deletecount, deletecount);
        }
        var data = {
            k: fieldId,
            v: (isAsc ? "ASC" : "DESC")
        };
        index = parseInt(index);
        if (isNaN(index) || index < 0 || index >= this.orders.length) {
            this.orders.push(data);
        } else {
            this.orders.splice(index, 0, data);
        }
        return this;
    };

    /**
     * 增加顺序排序条件（ASC）
     * @param {String} fieldId 对象属性名称
     * @param {Number} index 排序条件在数组内的插入位置（可选，默认增加到数组最后位置）
     * @returns {$Where} 对象本身
     */
    this.asc = function(fieldId, index) {
        return this.orderBy(fieldId, true, index);
    };

    /**
     * 增加倒叙排序条件（DESC）
     * @param {String} fieldId 对象属性名称
     * @param {Number} index 排序条件在数组内的插入位置（可选，默认增加到数组最后位置）
     * @returns {$Where} 对象本身
     */
    this.desc = function(fieldId, index) {
        return this.orderBy(fieldId, false, index);
    };

    /**
     * 移除排序条件处理
     * @param {String} fieldId 对象属性名称
     * @returns {$Where} 条件对象本身
     */
    this.removeOrder = function(fieldId) {
        if (!fieldId) return this;
        for (var i = 0, c = this.orders.length; i < c; i++) {
            var data = this.orders[i];
            if (data["k"] == fieldId) {
                this.orders.splice(i, 1);
                i--;
                c--;
            }
        }
        return this;
    };

    /**
     * 清空排序条件处理
     * @returns {$Where} 对象本身
     */
    this.clearOrder = function() {
        this.orders = [];
        return this;
    };

    // ------------------------ 查询辅助处理 ---------------------------

    /**
     * 连接查询条件处理（不修改本查询条件，返回新的查询条件）
     * @param {$Where} where 附加查询条件（可选，不设置或设置为 null 表示直接克隆本查询条件）
     * @param {Boolean} useNewOrder 是否使用新的排序条件（可选，默认：false）
     * @returns {$Where} 整合后的查询条件
     */
    this.union = function(where, useNewOrder) {
        var w = new $Where();
        for (var i = 0, c = this.wheres.length; i < c; i++) {
            w.wheres.push(this.wheres[i]);
        }
        if (!where || !useNewOrder) {
            for (var i = 0, c = this.orders.length; i < c; i++) {
                w.orders.push(this.orders[i]);
            }
        }
        if (where) {
            for (var i = 0, c = where.wheres.length; i < c; i++) {
                w.wheres.push(where.wheres[i]);
            }
            if (useNewOrder) {
                for (var i = 0, c = where.orders.length; i < c; i++) {
                    w.orders.push(where.orders[i]);
                }
            }
        }
        return w;
    };

    /**
     * 克隆查询条件对象
     * @param {Boolean} withOrder 是否克隆排序条件（可选，默认：true）
     * @returns {$Where} 新的查询条件
     */
    this.cloneWhere = function(withOrder) {
        var w = new $Where();
        for (var i = 0, c = this.wheres.length; i < c; i++) {
            w.wheres.push(this.wheres[i]);
        }
        if (withOrder != false) {
            for (var i = 0, c = this.orders.length; i < c; i++) {
                w.orders.push(this.orders[i]);
            }
        }
        return w;
    };

    /**
     * 清理所有条件（包括查询和排序）
     * @returns {$Where} 对象本身
     */
    this.clear = function() {
        this.wheres = [];
        this.orders = [];
        return this;
    };

}

// ===================================== TODO: 编辑器生成对象 =====================================

/**
 * 编辑器生成对象
 * @param {$Field} field 数据配置参数
 * @param {Number} initWidth 初始宽度（可选，0 表示使用默认值）
 * @param {Number} initHeight 初始高度（可选，0 表示使用默认值）
 */
function $Editor(field, initWidth, initHeight) {

    /**
     * 编辑框 HTML 元素对象
     */
    var _e = null;
    /**
     * 获取编辑框数据方法
     */
    var _valueFunc = null;
    /**
     * 设置编辑框数据方法
     */
    var _setValueFunc = null;
    /**
     * 设置编辑框焦点方法
     */
    var _focusFunc = null;
    /**
     * 移除元素辅助处理方法
     */
    var _removeFunc = null;
    /**
     * 对象本身引用
     */
    var _editor = this;

    // ----------------------------- 私有方法定义 --------------------------------

    /**
     * 设置输入框数据方法
     */
    var setInputFuncs = function() {
        _valueFunc = function() {
            return _e.value;
        };
        _setValueFunc = function(value) {
            _e.value = value;
        };
        _focusFunc = function() {
            _e.focus();
            _e.select();
        };
    };

    // Create edit element
    (function() {
        var e = null;
        var regstr = $G.trim(field.limitReg);
        var readOnly = !field.editable;
        var title = $Editor.getTitle(field);
        var formIndex = field.formIndex > 0 ? field.formIndex : 0;
        var formClass = (field.formClass && field.formClass.length > 0) ? field.formClass : null;
        var className;
        if (field.linked) {
            // Create selector
            e = new $DropBox($G.config["modulesFolder"] + "selector.html", field.linked.editMode, initWidth, initHeight, null, readOnly);
            var linked = field.linked;
            if (linked.actor) {
                e.setDataField(null, field);
            } else {
                e.setDatas(null, linked.datas, linked.id, linked.displayId, 0, linked.width, linked.height, linked.acceptInput);
            }
            e.getInput().title = title;
            if (formIndex > 0) e.getInput().tabIndex = formIndex;
            if (field.linked.editMode && regstr.length > 0) $Editor.limitInputText(e.getInput(), regstr);
            if (formClass) $W.addClassName(e.getInput(), formClass);
            _valueFunc = function() {
                return e.getDataValue();
            };
            _setValueFunc = function(value) {
                e.setDataValue(value);
            };
            _focusFunc = function() {
                e.focus();
            };
            _removeFunc = function() {
                e.close();
            };
            _e = e.getDropBox();
        } else if (field.booleanMark) {
            // Create checkbox
            var choose = field.booleanMark;
            _e = $W.createCheckBox(null, null, choose, null, readOnly, false);
            if (formIndex > 0) _e.tabIndex = formIndex;
            if (!readOnly) {
                $W.attachEvent(_e, "onkeydown", function(evt) {
                    var ev = $W.getEvent(evt);
                    var kc = $W.getEventKeyCode(ev);
                    if (kc == 32) return $W.cancelEventBubble(ev);
                    if (kc == 13 && !ev.ctrlKey) {
                        this.checked = !this.checked;
                        return $W.cancelEvent(evt);
                    }
                });
            }
            _e.className = "editor_checkbox" + (readOnly ? " readonly" : "") + (formClass ? " " + formClass : "");
            _valueFunc = function() {
                return _e.checked ? choose : "";
            };
            _setValueFunc = function(value) {
                _e.checked = (choose == value);
            };
            _focusFunc = function() {
                _e.focus();
            };
        } else {
            // Create simple editor
            var type = field.type;
            switch (type) {
                case $G.dataType.STRING:
                    if (field.max > 200 && !field.simpleLine) {
                        // Create textarea
                        _e = document.createElement("TEXTAREA");
                        _e.className = "editor_text_area" + (readOnly ? " readonly" : "") + (formClass ? " " + formClass : "");
                        if (readOnly) {
                            _e.disabled = true;
                            _e.readOnly = true;
                        }
                    } else {
                        // Create text input
                        className = "editor_text_input" + (readOnly ? " readonly" : "") + (formClass ? " " + formClass : "");
                        _e = $W.createTextInput(null, null, "", className, readOnly);
                    }
                    if (formIndex > 0) _e.tabIndex = formIndex;
                    _e.title = title;
                    if (!readOnly) {
                        $Editor.limitLength(_e, field.max);
                        if (regstr.length > 0) $Editor.limitInputText(_e, regstr);
                    }
                    setInputFuncs.call(_editor);
                    break;
                case $G.dataType.NUMBER:
                case $G.dataType.FLOAT:
                case $G.dataType.INT:
                    className = "editor_number_input noIme" + (readOnly ? " readonly" : "") + (formClass ? " " + formClass : "");
                    _e = $W.createTextInput(null, null, "", className, readOnly);
                    if (formIndex > 0) _e.tabIndex = formIndex;
                    if (formClass) $W.addClassName(_e, formClass);
                    _e.title = title;
                    if (!readOnly) {
                        if (regstr.length > 0) {
                            $Editor.limitInputText(_e, regstr);
                        } else {
                            if (type == $G.dataType.INT) {
                                $Editor.integerOnly(_e);
                            } else {
                                $Editor.floatOnly(_e);
                            }
                        }
                    }
                    setInputFuncs.call(_editor);
                    break;
                case $G.dataType.DATE:
                    e = new $DropBox($G.config["modulesFolder"] + "date.html", false, initWidth, initHeight, "date", readOnly);
                    e.getInput().style.textAlign = "center";
                    if (formIndex > 0) e.getInput().tabIndex = formIndex;
                    if (formClass) $W.addClassName(e.getInput(), formClass);
                    e.setDataField(null, field, 250, 220);
                    _valueFunc = function() {
                        return e.getDataValue();
                    };
                    _setValueFunc = function(value) {
                        e.setDataValue(value);
                    };
                    _focusFunc = function() {
                        e.focus();
                    };
                    _removeFunc = function() {
                        e.close();
                    };
                    _e = e.getDropBox();
                    break;
                case $G.dataType.PASSWORD:
                    // Cerate password box
                    _e = document.createElement("INPUT");
                    _e.type = "password";
                    _e.title = title;
                    if (formIndex > 0) _e.tabIndex = formIndex;
                    if (readOnly) {
                        _e.disabled = true;
                        _e.readOnly = true;
                        _e.className = "editor_password readonly";
                    } else {
                        _e.className = "editor_password noIme" + (formClass ? " " + formClass : "");
                        $Editor.limitLength(_e, field.max);
                        if (regstr.length > 0) $Editor.limitInputText(_e, regstr);
                    }
                    setInputFuncs.call(_editor);
                    break;
                default:
                    break;
            }
        }
        if (!_e) _e = $W.createDiv();
        if (!field.booleanMark) {
            if (initWidth && initWidth > 0) _e.style.width = initWidth + "px";
            if (initHeight && initHeight > 0) _e.style.height = initHeight + "px";
        }
    })();

    // ----------------------------- 公共方法定义 --------------------------------

    /**
     * 获取数据配置参数
     * @returns {$Field} 数据配置参数
     */
    this.getField = function() {
        return field;
    };

    /**
     * 设置编辑器焦点方法
     * @param {Number} time 延时时间（可选，不设置或设置为 null 表示立即设置）
     * @returns void
     */
    this.focus = function(time) {
        if (!_e || !_e.parentNode) return;
        if (time && time > 0) {
            window.setTimeout(function() {
                if (_focusFunc) {
                    _focusFunc.call(_editor);
                } else {
                    if (_e.focus) _e.focus();
                    if (_e.select) _e.select();
                }
            }, time);
        } else {
            if (_focusFunc) {
                _focusFunc.call(_editor);
            } else {
                if (_e.focus) _e.focus();
                if (_e.select) _e.select();
            }
        }
    };

    /**
     * 获取编辑器数据值（原始值，未修正）
     * @returns {Object} 当前值
     */
    this.value = function() {
        return _valueFunc ? _valueFunc.call(_editor) : null;
    };

    /**
     * 设置数据值处理
     * @param {Object} value 需要设置的编辑框当前值（非 data 对象）
     * @returns void
     */
    this.setValue = function(value) {
        if (_setValueFunc) _setValueFunc.call(_editor, value);
    };

    /**
     * 获取编辑器 HTML 元素对象
     * @returns {HTMLElement} HTML 元素对象
     */
    this.element = function() {
        return _e;
    };

    /**
     * 销毁编辑器对象方法
     */
    this.destory = function() {
        if (_removeFunc) _removeFunc.call(_editor);
    };

}

/**
 * 获取编辑器标题提示文本
 * @param {$Field} field 数据配置参数
 * @returns {String} 标题字符串
 */
$Editor.getTitle = function(field) {
    var title = "";
    if (field.editable) {
        if (field.linked) {
            title = $G.getText("acceptDrop");
        } else {
            var min = $G.toNumber(field.min, NaN);
            var max = $G.toNumber(field.max, NaN);
            if (!isNaN(min) && !isNaN(max)) {
                switch (field.type) {
                    case $G.dataType.STRING:
                        var regstr = $G.trim(field.limitReg);
                        if (regstr.length > 0) {
                            var note = regstr.replace(/[\[\]\^\(\)]/g, "");
                            note = note.replace(/\\w/g, $G.getText("regWords"));
                            note = note.replace(/\\d/g, $G.getText("regNumber"));
                            note = note.replace(/\\/g, $G.getText("regSperator"));
                            title = $G.getText("acceptReg", min, max, note);
                        } else {
                            title = $G.getText("acceptString", min, max, parseInt(max / 2));
                        }
                        break;
                    case $G.dataType.PASSWORD:
                        title = $G.getText("acceptPassword", min, max);
                        break;
                    case $G.dataType.INT:
                    case $G.dataType.FLOAT:
                    case $G.dataType.NUMBER:
                        title = $G.getText("acceptNumber", min, max);
                        break;
                    default:
                        break;
                }
            }
        }
    } else {
        title = $G.getText("readOnly");
    }
    return title;
};

/**
 * 根据数据配置对象修正数据为正确值
 * @param {Object} value 数据值
 * @param {$Field} field 数据配置参数
 * @param {Object} defaultValue 数据默认值（可选）
 * @returns {Object} 修正后的数据
 */
$Editor.fixValue = function(value, field, defaultValue) {
    switch (field.type) {
        case $G.dataType.STRING:
            return $G.trim(value);
        case $G.dataType.INT:
            return $G.toInt(value, defaultValue);
        case $G.dataType.FLOAT:
        case $G.dataType.NUMBER:
            return $G.toNumber(value, defaultValue);
        case $G.dataType.DATE:
            var d = $G.toDate(value, defaultValue);
            return d ? d.getTime() : null;
        case $G.dataType.BOOLEAN:
            return $G.toBoolean(value, defaultValue);
        default:
            break;
    }
    return value;
};

/**
 * 检查数值是否在最大值与最小值之间
 * @param {Number} n 需检查的数值
 * @param {Number} minV 最小值
 * @param {Number} maxV 最大值
 * @returns {Boolean} 是否成功
 */
$Editor.checkNumber = function(n, minV, maxV) {
    if (isNaN(n)) return false;
    if (typeof minV == "number") {
        if (n < minV) return false;
    }
    if (typeof maxV == "number") {
        if (n > maxV) return false;
    }
    return true;
};

/**
 * 检查数据值是否符合数据配置要求（返回值为 null 表示失败）
 * @param {String} name 数据格式错误时提示的名称
 * @param {Object} value 数据值（未修正的原始值）
 * @param {$Field} field 数据配置参数
 * @param {Number} types 错误时信息框按钮类型（如：$G.button.ok | $G.button.cancel）
 * @param {Function} callback 检查失败回调（可选，传入：type - 校验失败后点击的按钮类型：$G.button.xxx）
 * @returns {Object} 修正后的数据（返回值为 null 表示失败）
 */
$Editor.verifyValue = function(name, value, field, types, callback) {
    function showTypeErrMessage(textKey, minValue, maxValue, maxHalf) {
        var msg = $G.getText("dataInvalid", (name && name.length > 0) ? " \"" + $W.getNoticeText(name) + "\" " : $G.getText("data"));
        if (!isNaN(minValue) && !isNaN(maxValue)) {
            if (field.linked) {
                msg += $W.getGrayNote($G.getText("acceptDrop"));
            } else {
                msg += $W.getGrayNote($G.getText(textKey, minValue, maxValue, maxHalf));
            }
        }
        $W.showAlert(msg, callback, $G.icon.alarm, types);
    }
    var min = parseFloat(field.min);
    var max = parseFloat(field.max);
    switch (field.type) {
        case $G.dataType.STRING:
            var bl = $G.getBytesLength($G.trim(value));
            if (!$Editor.checkNumber(bl, min, max)) {
                showTypeErrMessage("acceptString", min, max, parseInt(max / 2));
                return null;
            }
            break;
        case $G.dataType.INT:
            var num = parseInt(value);
            if (!$Editor.checkNumber(num, min, max)) {
                showTypeErrMessage("acceptNumber", min, max);
                return null;
            }
            break;
        case $G.dataType.FLOAT:
        case $G.dataType.NUMBER:
            var num = parseFloat(value);
            if (!$Editor.checkNumber(num, min, max)) {
                showTypeErrMessage("acceptNumber", min, max);
                return null;
            }
            break;
        case $G.dataType.PASSWORD:
            var bl = $G.getBytesLength(value);
            if (!$Editor.checkNumber(bl, min, max)) {
                showTypeErrMessage("acceptPassword", min, max);
                return null;
            }
            break;
        default:
            break;
    }
    return $Editor.fixValue(value, field);
};

/**
 * 限制输入字符为指定正则表达式字符
 * @param {HTMLElement} e 元素对象
 * @param regstr 正则表达式字符（不接受输入的字符表达式）
 * @returns void
 */
$Editor.limitInputText = function(e, regstr) {
    if (!e || !regstr) return;
    regstr = $G.trim(regstr);
    if (regstr.length == 0) return;
    try {
        e.autocomplete = "off";
    } catch (ex) {
    }
    var reg = new RegExp(regstr, "img");
    $W.attachEvent(e, "onkeydown,onchange,onpaste", function(evt) {
        var ev = $W.getEvent(evt);
        switch (ev.type) {
            case "keydown":
                var k = $W.getEventKeyCode(ev);
                if ($G.isControlKey(k)) return true;
                var cs = $W.keyCodeToString(evt, false);
                if (!cs) return true;
                reg.lastIndex = 0;
                if (reg.test(cs)) return $W.cancelEvent(ev);
                break;
            case "paste":
                if (window.clipboardData) {
                    var t = window.clipboardData.getData("Text");
                    window.clipboardData.setData("Text", t.replace(reg, ""));
                    ev.returnValue = true;
                    return true;
                }
                var e = $W.getEventTarget(ev);
                reg.lastIndex = 0;
                if (e.value) e.value = e.value.replace(reg, "");
                break;
            case "change":
                var e = $W.getEventTarget(ev);
                reg.lastIndex = 0;
                if (e.value) e.value = e.value.replace(reg, "");
                break;
            default:
                break;
        }
        return true;
    });
};

/**
 * 限制只能输入浮点型数据
 * @param {HTMLElement} e 元素对象
 * @returns void
 */
$Editor.floatOnly = function(e) {
    $Editor.limitInputText(e, "[^\\-\\.\\d]");
};

/**
 * 限制只能输入整数（包括负号）
 * @param {HTMLElement} e 元素对象
 * @returns void
 */
$Editor.integerOnly = function(e) {
    $Editor.limitInputText(e, "[^\\-\\d]");
};

/**
 * 限制输入字符长度
 * @param {HTMLElement} e 对象
 * @param {Number} max 最大长度
 * @returns void
 */
$Editor.limitLength = function(e, max) {
    if (!e || !max || max <= 0) return;
    function subStringByByteLength(str, blen) {
        if (!str || blen == 0) return "";
        var l = 0;
        for (var i = 0; i < str.length; i++) {
            l += (str.charCodeAt(i) > 255 ? 2 : 1);
            if (l > blen) { return str.substring(0, i); }
        }
        return str;
    }
    try {
        e.autocomplete = "off";
    } catch (ex) {
    }
    $W.attachEvent(e, "onkeydown,onkeyup,onchange,onpaste", function(evt) {
        var ev = evt ? evt : window.event;
        var et = ev.target || ev.srcElement;
        var k = $W.getEventKeyCode(ev);
        switch (ev.type) {
            case "keydown":
                if ($G.isControlKey(k) || $G.getBytesLength(et.value) < max) return true;
                return $W.cancelEvent(ev);
            case "keyup":
            case "change":
                if ($G.getBytesLength(et.value) <= max) return true;
                et.value = subStringByByteLength(et.value, max);
                return $W.cancelEvent(ev);
            case "paste":
                if (window.clipboardData) {
                    var l = $G.getBytesLength(et.value);
                    var t = window.clipboardData.getData("Text");
                    var subs = subStringByByteLength(t, max - l);
                    window.clipboardData.setData("Text", subs);
                    ev.returnValue = true;
                }
                window.setTimeout(function() {
                    et.value = subStringByByteLength(et.value, max);
                }, 100);
                return true;
            default:
                break;
        }
    });
};

// ===================================== TODO: 下拉框对象类 =====================================

/**
 * 下拉框操作对象类
 * @param {HTMLElement} dropBox 下拉框外框元素或标识（必须）
 * @param {HTMLElement} icon 绑定点击动作后显示下拉框的图标元素或标识（可选，点击后隐藏或显示下拉框，如：下拉箭头按钮）
 * @param {String} url 需要显示的下拉页面地址（必须）
 * @param {Function} onShowing 显示前获取初始位置及数据方法（必须，传入：dropBox - 绑定下拉框的元素对象；返回：{top, left, width, height, data} 格式对象，返回 null 表示取消显示）
 * @param {Function} onShown 下拉框显示完毕处理，显示完成后才能往下拉框传输数据（可选，传入：iframe - 下拉框 IFRAME 对象，无返回值）
 * @param {Function} onClose 下拉框关闭回调（可选，传入：data - 下拉框回传的数据对象，无返回数据则不存在）
 * @param {Function} onData 下拉框操作过程回传数据方法（可选，传入：data - 下拉框回传的数据对象）
 * @param {String} className 下拉框样式（可选，默认："shadow"）
 */
function $DropDown(dropBox, icon, url, onShowing, onShown, onClose, onData, className) {

    /**
     * 下拉元素顶级对象
     */
    var _box = (typeof dropBox == "string" ? null : dropBox);
    /**
     * 下拉图标元素
     */
    var _icon = (typeof icon == "string" ? null : icon);
    /**
     * 下拉框 IFRAME 对象
     */
    var _iframe = null;
    /**
     * 是否点击了文档本身标识
     */
    var _myMouseDown = false;
    /**
     * 对象本身引用
     */
    var _drop = this;

    // ----------------------------- 私有方法定义 --------------------------------

    /**
     * 响应下拉框元素鼠标事件
     * @param {Event} evt 事件对象
     */
    var myMousedown = function(evt) {
        _myMouseDown = false;
        var e = $W.getEventTarget(evt);
        if ($W.contains(_icon, e)) {
            if (_drop.isShown()) {
                _drop.close();
            } else {
                _drop.show();
            }
            _myMouseDown = true;
        } else if ($W.contains(_box, e)) {
            _myMouseDown = true;
        }
        if (!_myMouseDown) _drop.close();
    };

    /**
     * 响应顶层文档对象鼠标事件
     * @param {Event} evt 事件对象
     */
    var topMousedown = function(evt) {
        if (!_drop.isShown()) return;
        if (_myMouseDown) {
            _myMouseDown = false;
            return;
        }
        var twin = $W.getTopWindow();
        var e = twin.$W.getEventTarget(evt);
        if ($W.contains(_iframe, e)) return;
        if (_iframe && _iframe.contentWindow) {
            var idoc = _iframe.contentWindow.document;
            if (idoc && $W.contains(idoc.documentElement, e)) return;
        }
        _drop.close();
    };

    /**
     * 响应本文档按键
     * @param {Event} evt 事件对象
     */
    var myKeydown = function(evt) {
        if ($W.getEventKeyCode(evt) == 27) {
            _drop.close();
            return $W.cancelEvent(evt);
        }
    };

    /**
     * 关闭下拉窗口处理
     * @param {Event} evt 事件对象
     */
    var doClose = function(evt) {
        _drop.close();
    };

    // ----------------------------- 公共方法定义 --------------------------------

    /**
     * 获取下拉框外框对象
     * @returns {HTMLElement} 下拉框外框对象
     */
    this.getDropBox = function() {
        return _box;
    };

    /**
     * 获取下拉框图标方法
     * @returns {HTMLElement} 下拉框图标对象
     */
    this.getIcon = function() {
        if (!_icon) _icon = $W.createDiv();
        return _icon;
    };

    /**
     * 显示下拉框处理
     * @returns {HTMLIFrameElement} 下拉框 IFRAME 对象（显示失败则返回 null）
     */
    this.show = function() {
        if (!$W.windowLoaded()) return null;
        if (_iframe) return _iframe;
        var data = onShowing.call(_drop, _box);
        if (!data) return null;
        $W.attachEvent($W.getTopWindow(), "onresize", doClose);
        $W.attachEvent($W.getTopWindow().document, "onmousedown", topMousedown);
        $W.attachEvent(window, "onunload", doClose);
        $W.attachEvent(document, "onkeydown", myKeydown);
        _iframe = $W.showDialog(url, data, false, _drop.close, onData, onShown, className, data.width, 30, data.left, data.top);
        return _iframe;
    };

    /**
     * 判断下拉框是否已经显示
     * @returns {Boolean} 是否已经显示
     */
    this.isShown = function() {
        return !!_iframe;
    };

    /**
     * 获取下拉框 IFRAME 对象
     * @returns {HTMLIFrameElement} 下拉框 IFRAME 对象
     */
    this.getIFrame = function() {
        return _iframe;
    };

    /**
     * 关闭下拉框处理（可传入多个参数）
     * @returns void
     */
    this.close = function() {
        if (!_iframe) return;
        $W.detachEvent($W.getTopWindow(), "onresize", doClose);
        $W.detachEvent($W.getTopWindow().document, "onmousedown", topMousedown);
        $W.detachEvent(window, "onunload", doClose);
        $W.detachEvent(document, "onkeydown", myKeydown);
        var args = [_iframe];
        if (arguments.length > 0) {
            args = args.concat($G.argumentsToArray.apply(this, arguments));
        }
        _iframe = null;
        $W.closeDialog.apply(this, args);
        if (onClose) onClose.apply(this, arguments);
    };

    /**
     * 发送事件按键到下拉框 IFRAME 对象
     * @param {Event} evt 事件对象
     * @returns void
     */
    this.sendEventToFrame = function(evt) {
        var ev = $W.getEvent(evt);
        if (ev.ctrlKey) return;
        var kc = $W.getEventKeyCode(evt);
        if (_drop.isShown()) {
            $W.sendEventToDialog(_iframe, evt);
        } else {
            var detail = $W.getEventWheel(evt);
            // Down or mouse wheel
            if (kc == 40 || detail != 0) {
                _drop.show();
                return $W.cancelEvent(evt);
            }
        }
    };

    // ----------------------------- 加载完成处理 --------------------------------

    (function() {
        $W.onload(function() {
            if (!_box) _box = $W.id(dropBox);
            $W.attachEvent(_box, "onmousedown", myMousedown);
            $W.attachEvent(_box, "onkeydown", _drop.sendEventToFrame);
            $W.attachOnMouseWheel(_box, function(evt) {
                if ($W.getEventWheel(evt) == 0) return;
                _drop.show();
                return $W.cancelEvent(evt);
            });
            if (!icon) return;
            if (!_icon) _icon = $W.id(icon);
            if ($W.isTag(_icon, "A")) {
                // 解决IE下显示IFRAME无效问题
                $W.attachEvent(_icon, "onclick", function(evt) {
                    return false;
                });
                _icon.hideFocus = true;
            }
        });
    })();
}

/**
 * 获取下拉框默认显示数据对象
 * @param {String} eid 定位元素对象或标识
 * @param {Object} data 需传输到下拉框的数据
 * @param {Number} width 下拉框宽度（可选，设置 null 或 0 则默认为定位元素宽度）
 * @param {Number} height 下拉框高度（可选，默认：250）
 * @return {Object} 显示数据对象（格式：{top, left, width, height, data}）
 */
$DropDown.getShowingData = function(eid, data, width, height) {
    var e = $W.id(eid);
    if (!e) return null;
    var epos = $W.getPosition(e, null, true);
    var pos = {
        top: epos.top + e.offsetHeight,
        left: epos.left,
        width: (width && width > 0 ? width : e.offsetWidth),
        height: (height && height > 0 ? height : 250),
        data: data
    };
    return pos;
};

/**
 * TODO: 文本输入下拉框类（使用 "dropdown" 样式）
 * @param {String} url 需要显示的下拉页面地址（必须）
 * @param {Boolean} editable 输入框是否可编辑（可选，默认不可编辑）
 * @param {Number} width 下拉框外框对象宽度（可选，默认自动扩展）
 * @param {Number} height 下拉框外框对象高度（可选，默认使用样式配置）
 * @param {String} iconClass 下拉图标样式（可选，默认使用下拉箭头）
 * @param {Boolean} readOnly 是否只读模式（可选，默认：false；设置为 true 时不显示下拉框）
 * @returns {$DropDown} 下拉框操作对象
 */
function $DropBox(url, editable, width, height, iconClass, readOnly) {

    /**
     * 下拉控制对象
     */
    var _drop = null;
    /**
     * 下拉框边框元素
     */
    var _box = null;
    /**
     * 下拉框输入对象
     */
    var _input = null;
    /**
     * 当前下拉框数据
     */
    var _data = null;
    /**
     * 下拉窗口数据配置对象
     */
    var _field = null;
    /**
     * 是否已取得焦点标识
     */
    var _isFocus = false;
    /**
     * 下拉查找延时句柄
     */
    var _itemoutId = 0;
    /**
     * 正在匹配查找的文字
     */
    var _findText = "";
    /**
     * 选择改变响应方法
     */
    var _onSelectedChanged = null;
    /**
     * 对象本身引用
     */
    var _dbox = this;

    // ----------------------------- 私有方法定义 --------------------------------

    /**
     * 得到焦点事件
     * @param {Event} evt 事件对象
     */
    var doFocus = function(evt) {
        if (editable) {
            window.setTimeout(function() {
                _input.select();
            }, 50);
        } else if (!_isFocus) {
            _isFocus = true;
            $W.addClassName(_box, "dropdown_edit_dis_focus");
        }
    };

    /**
     * 失去焦点事件
     * @param {Event} evt 事件对象
     */
    var doBlur = function(evt) {
        if (!_isFocus || (_drop && _drop.isShown())) return;
        var e = $W.getEventTarget(evt);
        if (!$W.contains(_box, e)) {
            _isFocus = false;
            $W.removeClassName(_box, "dropdown_edit_dis_focus");
        }
    };

    /**
     * 设置显示的数据
     * @returns void
     */
    var setDisplayValue = function() {
        if (editable) {
            _input.value = _dbox.getDataDiplay();
        } else {
            $W.removeChildNodes(_input);
            var text = _dbox.getDataDiplay();
            var limit = _field ? _field.textLimit : 0;
            if (limit && limit > 0) text = $G.limitString(text, limit);
            _input.appendChild(document.createTextNode(text));
        }
    };

    /**
     * 超时查找处理
     * @param {Event} evt 事件对象
     */
    var tiemoutToFind = function(evt) {
        if (!_drop) return;
        if (_itemoutId > 0) window.clearTimeout(_itemoutId);
        if (!_drop.isShown() || !_field) return;
        _itemoutId = window.setTimeout(function() {
            _itemoutId = 0;
            var txt = $G.trim(_input.value);
            if (txt == _findText) return;
            _findText = txt;
            $W.sendToDialog(_drop.getIFrame(), {
                type: "find",
                field: _field,
                value: _findText
            });
        }, evt ? 300 : 10);
    };

    // ----------------------------- 公共方法定义 --------------------------------

    /**
     * 绑定下拉框内容选择改变事件
     * @param {Function} func 选择改变响应方法（传入：newData - 新选中的数据对象, oldData - 之前选中的数据对象）
     * @returns void
     */
    this.onSelectedChanged = function(func) {
        _onSelectedChanged = func;
    };

    /**
     * 获取下拉框外框对象
     * @returns {HTMLElement} 下拉框外框对象
     */
    this.getDropBox = function() {
        return _box;
    };

    /**
     * 获取文本输入框对象（非编辑模式则为 TD 对象）
     * @returns {HTMLElement} 文本输入框
     */
    this.getInput = function() {
        if (!_input) _input = $W.createTextInput();
        return _input;
    };

    /**
     * 获取当前输入框文本
     * @returns {String} 输入框内的文本
     */
    this.getInputValue = function() {
        return $G.trim(editable ? _input.value : _dbox.getDataDiplay());
    };

    /**
     * 设置数据对象
     * @param {Object} data 下拉框表示的数据对象
     * @returns void
     */
    this.setData = function(data) {
        _data = data;
        if (_input) setDisplayValue.call(_dbox);
    };

    /**
     * 获取下拉框当前数据对象（无数据则返回 null）
     * @returns {Object} 当前数据对象
     */
    this.getData = function() {
        if (editable && _field && _field.linked) {
            var txt = $G.trim(_input.value);
            if (txt.length == 0) return null;
            if (txt != _dbox.getDataDiplay()) {
                var linked = _field.linked;
                var map = linked.map;
                var geted = false;
                var od = _data;
                map.foreach(function(k, v) {
                    if (v && v[linked.displayId] == txt) {
                        _data = v;
                        geted = true;
                        return false;
                    }
                });
                if (geted) {
                    if (_onSelectedChanged && !$G.compare(od, _data)) {
                        _onSelectedChanged.call(_dbox, _data, od);
                    }
                    return _data;
                }
                if (linked.actor && !linked.caching) {
                    var actor = linked.actor;
                    var where = new $Where().equals(linked.displayId, txt);
                    var result = new $Data().send(actor.props.command, "get", actor.props.version.get, where);
                    if (result.succeed() && result.data) {
                        _data = result.data;
                        map.put(_data[linked.id], _data);
                        if (_onSelectedChanged && !$G.compare(od, _data)) {
                            _onSelectedChanged.call(_dbox, _data, od);
                        }
                        return _data;
                    }
                }
                return null;
            }
        }
        return _data;
    };

    /**
     * 设置下拉框真实数据值
     * @param {Object} value 下拉框真实数据值
     * @returns void
     */
    this.setDataValue = function(value) {
        if (_field && _field.linked) {
            _data = $Actor.getLinkedData(null, _field, value);
        } else {
            _data = value;
        }
        if (_input) {
            if (editable && _field.linked && _field.linked.acceptInput) {
                _input.value = value;
            } else {
                setDisplayValue.call(_dbox);
            }
        }
    };

    /**
     * 获取下拉框真实数据值（无效则返回 null）
     * @returns {Object} 下拉框当前真实数据
     */
    this.getDataValue = function() {
        if (_field) {
            var linked = _field.linked;
            if (linked && editable && linked.acceptInput) return $G.trim(_input.value);
            var data = _dbox.getData();
            if (!data) return null;
            if (linked) return data[linked.id];
            return data[_field.id];
        }
        return _data;
    };

    /**
     * 获取下拉框显示数据值（返回非 null 字符串）
     * @returns {Object} 下拉框当前显示的值
     */
    this.getDataDiplay = function() {
        if (!_data) return "";
        if (_field) {
            if (_field.linked) return $G.formatValue(_data[_field.linked.displayId], $G.dataType.STRING, _field.format);
            return $G.formatValue(_data[_field.id], _field.type, _field.format);
        }
        return _data;
    };

    /**
     * 使用数组方式设置下拉框数据
     * @param {Object} data 输入框内显示的数据（字符串或数据对象）
     * @param {Array} dropDatas 下拉窗口显示的数据数组（可以使字符串数组或数据对象数组）
     * @param {String} valueKey 设置的数据是对象时，表示输入框表示的真实值属性字段（可选，不设置则把数据作为字符串处理）
     * @param {String} displayKey 设置的数据是对象时，表示在输入框内展示数据的属性字段（可选，不设置则把数据作为字符串处理）
     * @param {Number} textLimit 显示的字符内容最大字节长度（可选，超过则截取并增加 "..." 省略号）
     * @param {Number} dropWidth 下拉窗口宽度（可选，默认使用下拉框宽度）
     * @param {Number} dropHeight 下拉窗口高度（可选）
     * @param {Boolean} acceptInput 是否接受输入的文本内容（可选，只有同时启用编辑模式才有效，默认：false）
     * @returns void
     */
    this.setDatas = function(data, dropDatas, valueKey, displayKey, textLimit, dropWidth, dropHeight, acceptInput) {
        var datas;
        if (!valueKey || !displayKey) {
            valueKey = "text";
            displayKey = "text";
            datas = [];
            for (var i = 0, c = dropDatas.length; i < c; i++) {
                datas.push({
                    text: dropDatas[i]
                });
            }
            if (data) {
                data = {
                    text: data
                };
            } else {
                data = null;
            }
        } else {
            datas = dropDatas;
        }
        var map = new $HashMap();
        for (var i = 0, c = datas.length; i < c; i++) {
            var obj = datas[i];
            map.put(obj[valueKey], obj);
        }
        _data = data;
        _field = {
            id: valueKey,
            type: "string",
            textLimit: textLimit,
            gridColIndex: 1,
            gridCellClass: "left",
            inSelector: true,
            linked: {
                id: valueKey,
                displayId: displayKey,
                where: new $Where().asc(displayKey),
                datas: datas,
                map: map,
                width: dropWidth,
                height: dropHeight,
                acceptInput: acceptInput
            }
        };
        setDisplayValue.call(_dbox);
    };

    /**
     * 使用数据配置对象设置下拉框数据
     * @param {Object} data 当前数据对象
     * @param {$Field} field 数据配置对象
     * @param {Number} dropWidth 下拉窗口宽度（可选）
     * @param {Number} dropHeight 下拉窗口高度（可选）
     * @returns void
     */
    this.setDataField = function(data, field, dropWidth, dropHeight) {
        if (field) {
            if (dropWidth && dropWidth > 0) {
                if (field.linked) {
                    field.linked.width = dropWidth;
                } else {
                    field.dropWidth = dropWidth;
                }
            }
            if (dropHeight && dropHeight > 0) {
                if (field.linked) {
                    field.linked.height = dropHeight;
                } else {
                    field.dropHeight = dropHeight;
                }
            }
        }
        _data = data;
        _field = field;
        setDisplayValue.call(_dbox);
    };

    /**
     * 设置焦点方法
     */
    this.focus = function() {
        if (!$W.setFocus(_input) && _drop) {
            $W.setFocus(_drop.getIcon());
        }
        doFocus.call(_dbox);
    };

    /**
     * 显示下拉框方法
     */
    this.show = function() {
        if (_drop) _drop.show();
    };

    /**
     * 关闭下拉框方法
     */
    this.close = function() {
        if (_drop) _drop.close();
    };

    // ----------------------------- 创建下拉框元素 --------------------------------

    (function() {
        var tb = $W.createTable();
        _box = tb;
        if (width && width > 0) tb.style.width = width + "px";
        var tr = tb.insertRow(-1);
        var td = tr.insertCell(-1);
        if (height && height > 2) td.style.height = (height - 2) + "px";
        if (editable) {
            tb.className = "dropdown";
            td.className = "box";
            _input = $W.createTextInput();
            if (readOnly) {
                _input.readOnly = true;
                _input.disabled = true;
            } else {
                $W.attachEvent(_input, "onkeyup", tiemoutToFind);
            }
            td.appendChild(_input);
        } else {
            tb.className = "dropdown dropdown_edit_dis";
            td.className = "box noSelection";
            _input = td;
            $W.enableSelection(_input, false);
        }
        td = tr.insertCell(-1);
        if (height && height > 2) td.style.height = (height - 2) + "px";
        if (!readOnly) {
            var icon = $W.createA();
            if (iconClass && iconClass.length > 0) {
                icon.className = iconClass;
            }
            td.appendChild(icon);
            _drop = new $DropDown(tb, editable ? icon : tb, url, function(box) {
                _findText = "";
                var w = _field ? (_field.linked ? _field.linked.width : _field.dropWidth) : 0;
                var h = _field ? (_field.linked ? _field.linked.height : _field.dropHeight) : 0;
                return $DropDown.getShowingData(box, _data, w, h);
            }, function(iframe) {
                $W.sendToDialog(iframe, {
                    type: "field",
                    field: _field
                });
                if (editable && $G.trim(_input.value) != _dbox.getDataDiplay()) {
                    tiemoutToFind.call(_dbox, null);
                }
                _dbox.focus();
            }, function(data) {
                if (data && _data != data) {
                    var od = _data;
                    _data = data;
                    setDisplayValue.call(_dbox);
                    if (_onSelectedChanged && !$G.compare(od, data)) {
                        _onSelectedChanged.call(_dbox, data, od);
                    }
                }
                window.setTimeout(_dbox.focus, 100);
            });
        }
        if (!editable) $W.attachEvent(document, "onmousedown", doBlur);
        $W.attachEvent(_input, "onfocus", doFocus);
    })();

}

// ===================================== TODO: 扩展方法定义 =====================================

// 补充节点方法（获取当前文档的窗口对象）
if (!document.parentWindow) {
    document.parentWindow = window;
}

// 补充 String.startsWith 方法
if (typeof String.prototype.startsWith !== "function") {
    /**
     * 判断是否从某字符串开始
     * @param {String} s 要测试的字符串
     * @returns {Boolean} 以指定字符串开始
     */
    String.prototype.startsWith = function(s) {
        if (!s) this.version = null;
        return this.indexOf(s) == 0;
    };
}

// 补充 String.endsWith 方法
if (typeof String.prototype.endsWith !== "function") {
    /**
     * 判断是否从某字符串结束
     * @param {String} s 要测试的字符串
     * @returns {Boolean} 以指定字符串结束
     */
    String.prototype.endsWith = function(s) {
        var i = this.length - s.length;
        return i >= 0 && this.lastIndexOf(s) === i;
    };
}

// 补充 Array.indexOf 方法
if (typeof Array.prototype.indexOf !== "function") {
    /**
     * 查找数据在数组内首次出现的位置
     * @param {Object} value 需要查找的数据
     * @param {Number} i 开始位置
     * @returns {Number} 出现位置（-1表示未找到）
     */
    Array.prototype.indexOf = function(value, i) {
        i || (i = 0);
        var length = this.length;
        if (i < 0) i = length + i;
        for (; i < length; i++)
            if (this[i] == value) return i;
        return -1;
    };
}

// 补充 Array.lastIndexOf 方法
if (typeof Array.prototype.lastIndexOf !== "function") {
    /**
     * 查找数据在数组内最后出现的位置
     * @param {Object} value 需要查找的数据
     * @param {Number} i 开始位置
     * @returns {Number} 出现位置（-1表示未找到）
     */
    Array.prototype.lastIndexOf = function(value, i) {
        i = (!i && i != 0) ? this.length : (i < 0 ? this.length + i : i) + 1;
        var ary = this.slice(0, i);
        ary.reverse();
        var n = ary.indexOf(value);
        return (n < 0) ? -1 : i - n - 1;
    };
}

// 补充 Array.remove 方法
if (typeof Array.prototype.remove !== "function") {
    /**
     * 从数组内移除数据，返回移除前位置（-1表示未找到）
     * @param {Object} value 需要移除的数据
     * @returns {Number} 出现位置（-1表示未找到）
     */
    Array.prototype.remove = function(value) {
        var i = this.indexOf(value);
        if (i != -1) this.splice(i, 1);
        return i;
    };
}

// 补充 Array.removeAt 方法
if (typeof Array.prototype.removeAt !== "function") {
    /**
     * 从数组内移除数据，返回被移除的数据（返回 null 表示数据部存在）
     * @param {Number} i 需要移除的数据序号
     * @returns {Object} 被移除的数据
     */
    Array.prototype.removeAt = function(i) {
        if (i >= 0 && i < this.length) {
            var ary = this.splice(i, 1);
            if (ary && ary.length > 0) return ary[0];
        }
        return null;
    };
}

// 补充 Array.clear 方法
if (typeof Array.prototype.clear !== "function") {
    /**
     * 清空数组
     * @returns void
     */
    Array.prototype.clear = function() {
        this.length = 0;
    };
}

// ----------------------------------- 文件加载完毕初始化处理 -------------------------------------

(function() {

    // ----------- 初始化浏览器信息 ---------------

    // 获取浏览器信息
    var ua = window.navigator.userAgent.toLowerCase();
    var s, nav = $G.navigator;
    // 初始化浏览器类型
    if (s = ua.match(/msie\s([\d.]+)/)) {
        nav.ie = parseFloat(s[1]);
    } else if (s = ua.match(/trident.*rv:([\d.]+)/)) {
        nav.ie = parseFloat(s[1]);
    } else if (s = ua.match(/firefox\/([\d.]+)/)) {
        nav.firefox = parseFloat(s[1]);
    } else if (s = ua.match(/chrome\/([\d.]+)/)) {
        nav.chrome = parseFloat(s[1]);
    } else if (s = ua.match(/opera.([\d.]+)/)) {
        nav.opera = parseFloat(s[1]);
    } else if (s = ua.match(/version\/([\d.]+).*safari/)) {
        nav.safari = parseFloat(s[1]);
    }
    // 初始化网页链接信息
    $G.url.parse(window.location.href);

    // ------------ 为FIREFOX增加innerText方法 ---------------

    // 判断是否 FF 浏览器
    if ($G.navigator.firefox) {
        HTMLElement.prototype.__defineGetter__("innerText", function() {
            var anyString = "";
            var childS = this.childNodes;
            for (var i = 0; i < childS.length; i++) {
                if (childS[i].nodeType == 1) {
                    anyString += (childS[i].tagName == "BR") ? "\n" : childS[i].innerText;
                } else if (childS[i].nodeType == 3) {
                    anyString += childS[i].nodeValue;
                }
            }
            return anyString;
        });
        HTMLElement.prototype.__defineSetter__("innerText", function(sText) {
            this.textContent = sText;
        });
    }

    // ----------------- 显示调试信息 -----------------

    // 初始化语言标识
    $G.getLanguageKey();
    // 显示调试信息
    if ($G.config.debugMode) {
        $W.debug($G.getText("loadingPage"), $G.url.getPageRootFullUrl());
    }

    // ----------------- 屏蔽右键菜单 -----------------

    // 增加触发菜单事件响应
    $W.attachEvent(document, "oncontextmenu", function(evt) {
        var e = $W.getEventTarget(evt);
        if (e.type && $W.isTag(e, "INPUT")) {
            var type = e.type.toUpperCase();
            if (!e.disabled && (type == "TEXT" || type == "PASSWORD")) return true;
        }
        if (($W.isTag(e, "TEXTAREA") && !e.disabled) || $W.getSelectedText()) return true;
        return $W.cancelEvent(evt);
    });

    // ----------------- 向上传递鼠标事件 -----------------

    // 获取上级窗口对象
    var pwin = window.parent;
    // 获取事件触发IFRAME
    var iframe = window.frameElement;
    // 判断是否存在上级窗口
    if (pwin && iframe && pwin != window) {
        // 增加窗口按键处理，向上传递
        $W.attachEvent(document, "onmousedown", function(evt) {
            pwin.$W.fireEvent(iframe, "onmousedown");
        });
        // 增加窗口按键处理，向上传递
        $W.attachEvent(document, "onmouseup", function(evt) {
            pwin.$W.fireEvent(iframe, "onmouseup");
        });
    }

    // ----------------- 窗口加载完毕事件 -----------------

    // 判断是否支持 addEventListener
    if (document.addEventListener) {
        document.addEventListener("DOMContentLoaded", function() {
            document.removeEventListener("DOMContentLoaded", arguments.callee, false);
            $W.onWindowReady();
        }, false);
    }
    // 增加加载完毕处理
    $W.attachEvent(window, "onload", function(evt) {
        $W.detachEvent(window, "onload", arguments.callee);
        try {
            $W.onWindowReady();
        } catch (ex) {
            $W.debug("XXX Error: $W.onWindowReady() - " + ex.stack);
        }
        $W.attachEvent(document.body, "onkeydown", function(evt1) {
            var ev = $W.getEvent(evt1);
            $W.setOptionKeyDown(ev.shiftKey || ev.ctrlKey || ev.altKey);
        });
        $W.attachEvent(document.body, "onkeyup", function(evt1) {
            $W.setOptionKeyDown(false);
        });
        $W.attachEvent(document.body, "onselectstart", function(evt1) {
            if ($W.isEdit($W.getEventTarget(evt1))) return;
            if ($W.isOptionKeyDown()) return $W.cancelEvent(evt1);
        });
        try {
            if (iframe && (typeof iframe.onShown == "function")) {
                iframe.onShown(iframe);
                iframe.onShown = null;
            }
        } catch (ex) {
            $W.debug("XXX Error: $W.iframe.onShown() - " + ex.stack);
        }
    });
})();
