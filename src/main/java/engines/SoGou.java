package engines;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.BrowserKeyEvent.KeyModifiers;
import com.teamdev.jxbrowser.chromium.BrowserKeyEvent.KeyModifiersBuilder;
import com.teamdev.jxbrowser.chromium.BrowserMouseEvent.BrowserMouseEventBuilder;
import com.teamdev.jxbrowser.chromium.BrowserMouseEvent.MouseButtonType;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.DefaultNetworkDelegate;
import config.UA;
import tool.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import static com.teamdev.jxbrowser.chromium.BrowserMouseEvent.MouseButtonType.PRIMARY;
import static com.teamdev.jxbrowser.chromium.BrowserMouseEvent.MouseEventType.*;
import static com.teamdev.jxbrowser.chromium.BrowserMouseEvent.MouseScrollType.WHEEL_BLOCK_SCROLL;

public class SoGou {
   static  boolean show=true;
   static EnglishResolution.WidthHeiht widthHeiht = EnglishResolution.getEnglishResolution(EnglishResolution.PC);
    public static void main(String[] args) throws Exception {


       //如果168秒逻辑还没完成，无论是网卡了。还是什么情况，都结束进程；
        new Thread(new Monitor(168)).start();
        //这里是检测配置文件，是否需要换ip，配置文件里面的ipChange 这个参数；1是不换ip 0是换ip；
        if("1".equals(PropertyConfig.getPropertyV("ipChange").trim()))
        {
            MNET.ipChange();
            System.out.println("换IP....");
        }else {
            System.out.println("忘记没有换IP吗？");
        }
        //如果168秒逻辑还没完成，无论是网卡了。还是什么情况，都结束进程；
        new Thread(new Monitor(168)).start();
        //初始化jxbroser，简单来讲就是，初始化浏览器，里面我采用的是 cpu渲染，可选gpu，但是生成环境是linux无界面版，gpu也没用；
        Basic.init();
        Browser browser = Basic.getBroser();
        //设置浏览器头
        BrowserPreferences.setUserAgent(UA.getUa(UA.PC));
        //设置浏览器的可视宽高
        browser.setSize(widthHeiht.getWidth(), widthHeiht.getHeight());
        String doman=null,  searchwords=null;
        try {
            System.out.println("***************测试大量点击，没有展示...***********************");
            List<String> stringList=new ArrayList();
            //这段是读取配置文件，目的是找到域名，和你要的关键词；没什么明确意义
            for(int i=0;i<500;i++){
                String str= PropertyConfig.getPropertyV("test"+i);
                if(str!=null&&str.trim().length()>2){
                    stringList.add(str);
                    System.out.println(str);
                }
            }
            if(stringList.size()>0){
                //这个就是筛选出，你写的概率最大的那个词，你理解就是随机就好；
                String item= L.shenChoose(stringList);
                doman= RegexParse.baseParse(item,"doman=([\\S]*?);",1);
                searchwords= RegexParse.baseParse(item,"searchWords=([\\S]*?);",1);
                System.out.println("当前准备运行的是:"+doman+">>"+searchwords);
                if(item!=null&&doman!=null){
                    //这里是真正的逻辑；
                    logic(browser, doman, searchwords, widthHeiht);
                }
            }
        }catch (Throwable e){
            System.exit(0);
        }
    }

    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    /***
     * 处理关键逻辑的
     * @param browser
     * @param doman
     * @param searchwords
     * @param widthHeiht
     * @throws Exception
     */
    static void logic(Browser browser, String doman, String searchwords, EnglishResolution.WidthHeiht widthHeiht) throws Exception {
        //这个地方是设置referer，让对方误以为是从qq来的
        browser.getContext().getNetworkService().setNetworkDelegate(new DefaultNetworkDelegate(){
            @Override
            public void onBeforeSendHeaders(BeforeSendHeadersParams params) {
                try {
                    if(params.getURL().contains("&et=")||params.getURL().contains("&si=")){
                        String Referer="https://www.qq.com/";
                        params.getHeadersEx().setHeader("Referer",Referer);
                        params.getHeaders().setHeader("Referer",Referer);;
                    }
                } catch (Exception e) {
                    System.out.println("替换referre失败...");
                }

            }
        });
        //监听子页面是否打开，也就是你的网站，然后检测打开了，就可以关闭了。因为引擎无法检测你的内页；
        browser.setPopupHandler(new PopupHandler() {
            public PopupContainer handlePopup(PopupParams params) {
                return new PopupContainer() {
                    public void insertBrowser(final Browser browser, final Rectangle initialBounds) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                //下面这段就是现实和不显示，没什么特别作用。就是有界面浏览器和无界面浏览器这么理解就好；
                                    BrowserView browserView = new BrowserView(browser);
                                    browserView.setPreferredSize(initialBounds.getSize());
                                    final JFrame frame = new JFrame();
                                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                                    frame.add(browserView, BorderLayout.CENTER);
                                    frame.pack();
                                    frame.setSize(1024, 768);
                                    frame.setLocation(initialBounds.getLocation());
                                    frame.setVisible(true);
                                System.out.println("进入监听");
                                //这个循环就是检测，是否已经进入到了你的目标网站，如果进入了你的网站，就退出来
                                while (true){
                                    //这段是个逻辑，首先域名是你的域名，然后又不是搜狗的域名，就认为是你的网站
                                    if(browser.getURL().contains(doman)&&!browser.getURL().contains("sogou"))
                                    {
                                        System.out.println("============================================================");
                                        System.out.println("$$$$$$$$$$$$$$$$$$$$点击成功:"+browser.getURL());
                                        System.out.println("============================================================");
                                        //注销浏览器，实际生产环境是经常出问题的，所以我直接超时结束掉进程
                                        browser.dispose();
                                        System.exit(0);
                                    }
                                    try {
                                        Thread.sleep(500);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                };
            }
        });

        //监听页面是否已经打开，然后找到你要点击的元素，点击
        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                //判断是否是已经被引擎封锁了。以为你是爬虫
                if(browser.getURL().contains("antispider")){
                    System.out.println("封锁了...");
                    System.exit(0);
                }
                //检测打开的是不是搜狗的站内页
                if(browser.getURL().contains("site")){
                    //开始鼠标晃动+自动点击等功能;
                    DOMDocument doc = browser.getDocument();
                    List<DOMElement> rb = doc.findElements(By.className("rb"));
                    List<DOMElement> vrwrap = doc.findElements(By.className("vrwrap"));
                    System.out.println("主页已经加载完毕:是否有点击元素组" + (rb != null) + (vrwrap != null) + ">url:" + browser.getURL());
                    //判断主页是否加载完毕，然后进行点击;
                    boolean isLoad = true;
                    //循环查找元素，判断里面是否有自己的网站，之所以下面有两个for循环是因为 对方有图片的元素和没有图片的元素不一样，所以我才写了2个；
                    for (DOMElement item : rb) {
                        //判断有自己的网站，然后进入点击逻辑；大概意思是这个元素包含了你配置的域名
                        if (item.getInnerHTML().contains(doman)) {
                            isLoad = false;
                            System.out.println("********找到匹配项,开始准备点击1*********");
                            //点击元素
                            excute(browser, item, widthHeiht);
                            System.out.println("*******************************");
                            System.out.println(item.getInnerText());
                            System.out.println("*******************************");
                            break;
                        }
                    }
                    ;
                    if (isLoad)
                        for (DOMElement item : vrwrap) {
                            if (item.getInnerHTML().contains(doman)) {
                                System.out.println("********找到匹配项,开始准备点击2*********");
                                excute(browser, item, widthHeiht);
                                System.out.println("*******************************");
                                System.out.println(item.getInnerText());
                                System.out.println("*******************************");
                                break;
                            }
                        }
                }
            }
        });
        String url="https://www.sogou.com/tx?site="+doman.trim()+"&query="+searchwords.trim();
      browser.loadURL(url);
    }

    /**
     * 无需关心
     */
    private static void forwardMousePressEvent(Browser browser,
                                               MouseButtonType buttonType,
                                               int x,
                                               int y,
                                               int globalX,
                                               int globalY) {
        BrowserMouseEventBuilder builder = new BrowserMouseEventBuilder();
        builder.setEventType(MOUSE_PRESSED)
                .setButtonType(buttonType)
                .setX(x)
                .setY(y)
                .setGlobalX(globalX)
                .setGlobalY(globalY)
                .setClickCount(1)
                .setModifiers(new KeyModifiersBuilder().mouseButton().build());
        browser.forwardMouseEvent(builder.build());
    }

    /**
     * 无需关心
     */
    private static void forwardMouseReleaseEvent(Browser browser,
                                                 MouseButtonType buttonType,
                                                 int x,
                                                 int y,
                                                 int globalX,
                                                 int globalY) {
        BrowserMouseEventBuilder builder = new BrowserMouseEventBuilder();
        builder.setEventType(MOUSE_RELEASED)
                .setButtonType(buttonType)
                .setX(x)
                .setY(y)
                .setGlobalX(globalX)
                .setGlobalY(globalY)
                .setClickCount(1)
                .setModifiers(KeyModifiers.NO_MODIFIERS);
        browser.forwardMouseEvent(builder.build());
    }
    /**
     * 无需关心
     */
    private static void forwardMouseClickEvent(Browser browser,
                                               MouseButtonType buttonType,
                                               int x,
                                               int y,
                                               int globalX,
                                               int globalY) {
        forwardMousePressEvent(browser, buttonType, x, y, globalX, globalY);
        forwardMouseReleaseEvent(browser, buttonType, x, y, globalX, globalY);
    }

    /**
     * 无需关心
     * @param browser
     * @param unitsToScroll
     * @param x
     * @param y
     */
    private static void forwardMouseScrollEvent(Browser browser,
                                                int unitsToScroll,
                                                int x,
                                                int y) {
        BrowserMouseEventBuilder builder = new BrowserMouseEventBuilder();
        builder.setEventType(MOUSE_WHEEL)
                .setX(x)
                .setY(y)
                .setGlobalX(0)
                .setGlobalY(0)
                .setScrollBarPixelsPerLine(25)
                .setScrollType(WHEEL_BLOCK_SCROLL)
                .setWindowX(widthHeiht.width)
                .setWindowY(widthHeiht.height)
                .setUnitsToScroll(unitsToScroll);
        browser.forwardMouseEvent(builder.build());
    }


    /***
     * 执行点击
     * @param browser
     * @param domElement
     * @param widthHeiht
     */
    public static void excute(Browser browser, DOMElement domElement, EnglishResolution.WidthHeiht widthHeiht) {
        try {
            //鼠标模拟鼠标轨迹
            Basic.randomMove(browser,widthHeiht.width,widthHeiht.height);
            Rectangle  rectangle = domElement.getBoundingClientRect();
            System.out.println(">>>>>" + rectangle.x + "|" + rectangle.y + "|" + rectangle.width + "|" + rectangle.height + "|" + widthHeiht.width + "|" + widthHeiht.height);
           //找到要点击的元素
            domElement=domElement.findElement(By.xpath("h3/a"));
            Basic.move(browser, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            //browser.executeJavaScript("window.scrollTo(" + rectangle.x + "," + (rectangle.y-(100+(L.randomNumber(1,68)))) + ");");
            Basic.randomMove(browser,widthHeiht.width,widthHeiht.height);
           //鼠标移动到要点击的元素
            Basic.targetMove(browser,domElement);
            System.out.println("___________________________________________________________");
            //将滚动条晃动到人常看的位置
            domElement.scrollToBottom();
            rectangle = domElement.getBoundingClientRect();
            //开始点击这个元素,这个函数底层就不用看了。是基本函数
            forwardMouseClickEvent(browser, PRIMARY, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
