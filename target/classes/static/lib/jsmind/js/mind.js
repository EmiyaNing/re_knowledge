(function($,$w){
    //jsmind容器，根据具体界面提供的元素来写
    var CONTAINER_ID = "jsmindcontainer";
    // 容器的id
    var $d = $w.document;
    //获取jsmind的容器的dom节点
    var $container = $d.getElementById(CONTAINER_ID);
    var _h_header = $d.getElementsByClassName(".rightbox").clientHeight;
    //初始化jsmind对象
    console.log("开始初始化该文件");
    var _jm = new jsMind({
        container:CONTAINER_ID,
        editable:true,
        theme:'orange'
    });
    /**
     * 中间逻辑实现代码。
     * 
     */ 
    //通过正则表达式从url中获取思维导图的名称。
    var get_mind_id = function(){
        return location.href.match(/([a-zA-z0-9]{32})(#.*)?/)[1];
        //该正则表达式的作用为：匹配英文字母以及数字的最长32个字符。
    }
    //获取url。得改
    var get_mind_url = function(){
        return location.href.split('#')[0] + '.jm';
    }

    var load_ajax_mind = function(url){
        jsMind.util.ajax.get(url,function(mind){
            _jm.show(mind);
        });
    }


    //实现思维导图更名以及保存。 
    var on_mind_name_changed = function(data){
        if(data.success){
            var mind_name = $('#').val();
            $('#').text(mind_name);//实现更名操作
            toggle_edit_mind_name();
        }
    }

    var toggle_edit_mind_name = function(e){
        $('#mind_name_link, #mind_name_edit_panel').toggleClass('layui-hide');                //注意layui中对于隐藏实现的class
        return false;
    }

    var change_mind_name = function(e){
        var mind_id = get_mind_id();
        var origin_mind_name = $('#').text();       //思维导图标题栏的id
        var mind_name = $('#').val();               //提取输入栏中的内容
        if(origin_mind_name == mind_name){
            toggle_edit_mind_name();
            return false;
        }
        $.ajax({
            url:'',
            type:'POST',
            data:{
                "mind-id":mind_id,
                "mind-name":mind_name
            },
            success:on_mind_name_changed
        });
        return false;
    }

    //回掉函数，用来展示显示完成时使用的。
    var on_mind_saved = function(data){
        if(data.success){
        }
    }

    var save_map = function(e){
        $.ajax({
            url:'',//
            type:'POST',
            data:JSON.stringify(_jm.get_data()),
            contentType:"application/json; charset=utf-8",
            dataType:'json',
            success: on_mind_saved
        });
        return false;
    }


    /**
     * 添加子节点或同级节点的函数。 
     */
    var add_sibling_node = function(){
        _jm.shortcut.handles['addbrother'](_jm,e);
        return false;
    }

    var add_child_node = function(e){
        _jm.shortcut.handles['addchild'](_jm,e);
        return false;
    }

    /**
     * 删除节点通过jsmind库提供的函数来完成。 
     */
    var remove_node = function(e){
        _jm.shortcut.handles['delnode'](_jm,e);
        return false;
    }

    var clear_map = function(e){
        curr_mind = _jm.mind;
        _jm.show();
        _jm.mind.author = curr_mind.author;
        _jm.begin_edit(_jm.mind.root);
        return false;
    }

    /**
     * 思维导图删除函数，后端在处理相关url的时候得注意返回相关的message。
     */ 
    var on_mind_deleted = function(data){
        if(data.success){
            back_to_list();
        }
    }

    var destory_map = function(e){
        $.ajax({
            url:'',//
            type:'GET',
            contentType:"application/json; charset=utf-8",
            dataType:'json', 
            success: on_mind_deleted
        });
        return false;
    }

    var back_to_list = function(e){
        location.href = '/mind/';       //url得改。。
        return false;       
    }



    /**
     * 接下来的这段代码，不断设置思维导图的当前位置，每个300毫秒设置一次，如果设置的过程超时了
     * 遍将setTimeout函数对变量_resize_timeout_id的改变清除。
     * 这仅仅是初步的解读，作者这么设置的思想我还是无法完全理解。
     */ 
    var set_container_size = function(){
        var ch = $w.innerHeight - _h_header; //这个段代码得改，根据到时候的界面布局。
        var cw = $w.innerWidth;
        $container.style.height = ch + 'px';
        $container.style.width  = cw + 'px';
    }

    var _resize_timeout_id = -1;
    var reset_container_size = function(){
        if(_resize_timeout_id != -1){
            clearTimeout(_resize_timeout_id);
        }
        _resize_timeout_id = setTimeout(function(){
            _resize_timeout_id = -1;
            set_container_size();
            _jm.resize();
        },300);
    }

    /**
     * 该函数功能目前尚不清楚，，，，，，，难受。。源代码部分也没有完全看懂。
     */ 
    var register_event = function(){
        jsMind.util.dom.add_event($w,'resize',reset_container_size);
    }

    var init_action_menu = function(){
        $('#mind_name_link').click(toggle_edit_mind_name);//注册“现实思维导图名称编辑栏现实事件”
        $('#mind_name_edit_panel').click(change_mind_name);     //注册“保存更改的思维导图名称事件”，与上一个函数功能配套。
        $('#').click(save_map);             //注册“保存当前思维导图的事件”
        $('#').click(add_sibling_node);     //注册添加同级节点事件。
        $('#').click(add_child_node);       //注册“添加自节点时间”
        $('#').click(remove_node);          //注册“删除该节点事件”
        $('#').click(clear_map);            //注册“清空思维导图事件”
        $('#').click(destory_map);          //注册“销毁思维导图事件”
        $('#').click(back_to_list);         //注册“返回上级界面事件”
    }

    var page_load = function(){
        console.log("该文件初始化成功!!");
        register_event();
        set_container_size();
        load_ajax_mind(get_mind_url());     //该函数方式待定，等确定完后端存储，获取数据方式之后再确定。
        init_action_menu();
    }

    page_load();
})(jQuery,window);