/**
 * 
 */
package com.lghai.word;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyle;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STStyleType;


public class WordUtil {
	/** 1厘米 */
	public static final int ONE_UNIT = 567;
	/** 页脚样式 */
    public static final String STYLE_FOOTER = "footer";
    
    /** 页眉样式 */
    public static final String STYLE_HEADER = "header";
    /** 语言，简体中文 */
    public static final String LANG_ZH_CN = "zh-CN";
	
	public static void main(String[] args) throws Exception {
		String contents ="<titlename>孟子</titlename>"; 
		contents +="<h1>人物生平</h1>"; 
		contents +="<h2>早年受教</h2>"; 
		contents +="<p>孟子[1]  的出生之时距孔子之死（公元前479年）大约百年左右，活动年代约在公元前372年至前289年。他是鲁国贵族孟孙氏的后裔。孟孙氏衰微后，孟子有一支从鲁迁居到邹，就是孟子的祖先。《史记·孟子荀卿列传》说，孟子“受业子思之门人”；孟子没有讲他的老师的姓名，却是说：“予未得为孔子徒也，子私淑诸人也。”[2]  其受业于何人，自汉代以来颇有争议，一是认为师从子思，一是师从子上，一是师从子思之门人。朱熹云：“私，犹窃也。淑，善也。李氏以为方言是也。人，谓子思之徒也。”[3]  司马迁在《史记·孟子荀卿列传》中记载，孟子“受业子思之门人”。[4]  而且根据《史记·孔子世家》，子思的父亲孔鲤（孔子的儿子）生卒年，鲁缪公（鲁穆公）的在位时间进行推算，孟子受业于子思是难以成立的。由此看来，孟子师从子思之门人较为妥贴。</p>"; 
		contents +="<p>关于孟子的父亲更加缺乏资料，赵岐在《孟子题辞》中认为孟子“宿丧其父，幼被慈母三迁之教”。孟子幼年和孔子一样，在母亲的教育下成长，孟母教子的故事，史书上记载颇多，孟母的言传身教对孟子成为“亚圣”具有巨大的作用。孟子对孔子备极尊崇，他在《公孙丑上》说：“自生民以来，未有盛于孔子也”。“乃所愿，则学孔子也”。孟子曾经游历齐、宋、滕、魏、鲁等国，前后有二十多年。他游历列国的具体时间，已说不十分准确，只能依据《孟子》一书的记载，说明大体上的时间和情况。</p>"; 
		contents +="<h2>游说齐宋</h2>"; 
		contents +="<p>孟子继承了孔子的仁政学说，是位非常有抱负的政治家，在诸侯国合纵连横，战争不断时期，作为锐捷的思想家，孟子意识到了当时的时代特征和趋势，建构了自己的学说。与孔子一样，他力图将儒家的政治理论和治国理念转化为具体的国家治理主张，并推行于天下。而当时各个思想家为了实现自己的政治主张，游说各国诸侯。在这样的社会背景下，孟子开始周游列国，游说于各国君主之间，推行他的政治主张。</p>"; 
		contents +="<p>孟子大约在45岁之前率领弟子出游各国。孟子第一次到齐国，是在齐威王（公元前356年至前320年）年间。当时匡章背着“不孝”的坏名声，孟子却“与之游，又从而礼貌之”[2]  。到了齐国，孟子宣扬他的“仁政无敌”[5]  主张，他在齐国很不得志，连威王赠送的“兼金一百”镒，[6]  都没有接受，就离开齐国。</p>"; 
		contents +="<p>公元前329年左右，宋公子偃自立为君的时候，孟子到了宋国。他在宋国期间，滕文公还是世子，他去楚国经过宋国时见到孟子。“孟子道性善，言必称尧舜。”[7]  他从楚国回来又在宋国见到孟子。孟子说：“世子疑吾言乎？夫道一而已矣。”[7]  意思是说，只要好好地学习“先王”，就可以把滕国治理好。不久，孟子接受了宋君馈赠的七十镒金，离开宋国，回到邹国。《梁惠王下》记载说，邹国同鲁国发生了冲突。邹穆公问孟子：“吾有司死者三十三人，而民莫之死也。诛之，则不可胜诛；不诛，则疾视其长上之死而不救。如之何则可也？”孟子回答说：“凶年饥岁，君之民老弱转乎沟壑，壮者散而之四方者，几千人矣；而君之仓廪实，府库充，有司莫以告，是上慢而残下也。”他说，这就象曾子说的那样：你怎样对待人家，人家就将怎样回报你。现在，您的百姓可得到报复的机会了，您不要责备他们吧！“君行仁政，斯民亲其上，死其长矣。”滕定公死了，滕文公使然友两次到邹国来向孟子请教怎样办理丧事。滕文公嗣位，孟子便来到滕国。滕文公亲自向孟子请教治理国家的事情。孟子说：“民事不可缓也。”他认为人民有了固定产业收入，才有稳定的思想道德和社会秩序。而人民生活有了保障后，还必须对之进行“人伦”的教化。“人伦明於上，小民亲於下”。滕文公又派他的臣子毕战询问井田制的情况。孟子说：“夫仁政，必自经界始”。接着讲了一遍井田制。最后说，我说的是大概情况，您和您的国君参照着去做吧。</p>"; 
		contents +="<p>“有为神农之言者”农家许行，从楚国赶到滕国来。许行主张君民并耕而食，反对不劳而获的剥削、压迫；主张实物交易，物品在数量、重量上相等的，价格相同。陈相兄弟很赞成许行的主张，“尽弃其学而学焉”。许行的思想在反对剥削上是有进步意义的。但他以小农的平均主义思想否定社会分工，是违反社会历史发展规律的。孟子抓住许行的这一弱点，大讲“物之不齐”的道理，并以“劳心”“劳力”的划分来论证剥削制度、阶级压迫的“合理性”。</p>"; 
		contents +="<h2>奔赴魏国</h2>"; 
		contents +="<p>孟子看得很清楚，滕国的自身都难保，根本谈不上实行他的政治主张。他在梁惠王后元十五年（公元前320年），离开滕国到了魏国。这时，孟子已经五十三岁。惠王见到孟子就问：“叟，不远千里而来，亦将有以利吾国乎？”孟子最反对国君言利，所以回答说：“王，何必曰利，亦有仁义而已矣。”</p>"; 
		contents +="<break></break>"; 
		contents +="<h1>主要功绩</h1>"; 
		contents +="<h2>思想</h2>"; 
		contents +="<p>《史记》说孟子有著述七篇传数世，《汉书·艺文志》说有十一篇。东汉末赵岐说孟子有《性善辩》、《文说》、《孝经》、《为政》四篇外书，则十一篇当是在七篇外又加外书四篇。赵岐认为外书四篇内容肤浅，与内篇不合，当是后人所作。流传至今的《孟子》，即赵岐所说的内篇。全书虽非孟子手笔，但为孟子弟子所记，皆为孟子言行无疑。从书中看出孟子有如下一些言论和思想：在人性方面，主张性善论。以为人生来就具备仁、义、礼、智四种品德。人可以通过内省去保持和扩充它，否则将会丧失这些善的品质。因而他要求人们重视内省的作用。在社会政治观点方面，孟子突出仁政、王道的理论。仁政就是对人民“省刑罚，薄税敛。”他从历史经验总结出“暴其民甚，则以身弑国亡，”又说三代得天下都因为仁，由于不仁而失天下。强调发展农业，体恤民众，关注民生，他在《寡人之于国也》中说：“七十者衣帛食肉，黎民不饥不寒，然而不王者，未之有也。”[13]  他又提出民贵君轻的主张，认为君主必须重视人民，“诸侯之宝三，土地、人民、政事。”君主如有大过，臣下则谏之，如谏而不听可以易其位。至于像桀、纣一样的暴君，臣民可以起来诛灭之。他反对实行霸道，即用兼并战争去征服别的国家；而应该行仁政，争取民心的归附，以不战而服，也即他所说的“仁者无敌”，实行王道就可以无敌于天下。在价值观方面，他强调舍身取义，“生，亦我所欲也；义，亦我所欲也。二者不可得兼，舍生而取义者也。”强调要以“礼义”来约束自己的一言一行，不能为优越的物质条件而放弃礼义，“万钟则不辨礼义而受之，万钟于我何加焉！”[14] </p>"; 
		contents +="<h3>民本思想</h3>"; 
		contents +="<p>孟子根据战国时期的经验，总结各国治乱兴亡的规律，提出了一个富有民主性精华的著名命题：“民为贵，社稷次之，君为轻”。认为如何对待人民这一问题，对于国家的治乱兴亡，具有极端的重要性。孟子十分重视民心的向背，通过大量历史事例反复阐述这是关乎得天下与失天下的关键问题。</p>"; 
		contents +="<p>“民为贵，社稷次之，君为轻。”意思是说，人民放在第一位，国家其次，君在最后。孟子认为君主应以爱护人民为先，为政者要保障人民权利。孟子赞同若君主无道，人民有权推翻政权。正因此原因，《汉书》「艺文志」仅仅把《孟子》放在诸子略中，视为子书，没有得到应有的地位。到五代十国的后蜀时，后蜀主孟昶命令人楷书十一经刻石，其中包括了《孟子》，这可能是《孟子》列入「经书」的开始。到南宋的孝宗时，朱熹将《孟子》与《论语》、《大学》、《中庸》合在一起称「四书」，并成为「十三经」之一，《孟子》的地位才被推到了高峰。传说明太祖朱元璋因不满孟子的民本思想，曾命人删节《孟子》中的有关内容。</p>"; 
		contents +="<h3>仁政学说</h3>"; 
		contents +="<p>孟子继承和发展了孔子的德治思想，发展为仁政学说，成为其政治思想的核心。孟子的政治论，是以仁政为内容的王道，其本质是为封建统治阶级服务的。他把“亲亲”、“长长”的原则运用于政治，以缓和阶级矛盾，维护封建统治阶级的长远利益。</p>"; 
		contents +="<p>孟子说：“夫仁政，必自经界始”。所谓“经界”，就是划分整理田界，实行井田制。孟子所设想的井田制，是一种封建性的自然经济，以一家一户的小农为基础，采取劳役地租的剥削形式。每家农户有五亩之宅，百亩之田，吃穿自给自足。孟子认为，“民之为道也，有恒产者有恒心，无恒产者无恒心”，只有使人民拥有“恒产”，固定在土地上，安居乐业，他们才不去触犯刑律，为非作歹。孟子认为，人民的物质生活有了保障，统治者再兴办学校，用孝悌的道理进行教化，引导他们向善，这就可以造成一种“亲亲”、“长长”的良好道德风尚，即“人人亲其亲、长其长，而天下平”。孟子认为统治者实行仁政，可以得到天下人民的衷心拥护，这样便可以无敌于天下。孟子所说的仁政要建立在统治者的“不忍人之心”的基础上。孟子说：“先王有不忍人之心，斯有不忍人之政矣。”“不忍人之心”是一种同情仁爱之心。但是，这种同情仁爱之心不同于墨子的“兼爱”，而是从血缘的感情出发的。孟子主张，“亲亲而仁民”，“老吾老以及人之老，幼吾幼以及人之幼”。仁政就是这种不忍人之心在政治上的体现。</p>"; 
		
		createWord("孟子",contents,"中国文学",true);
	}
	
	public static String createWord(String filename, String contents, String pageHeader, boolean  pageFooter) throws Exception {
		String path="F:/Users/Unruly/Desktop/智能文档/";
		File f = new File(path);
		if(!f.exists()){
			f.mkdirs();
		}
		
		XWPFDocument docxDocument = new XWPFDocument();
		
		Document doc = Jsoup.parseBodyFragment(contents);
		Element body = doc.body();
		Elements es = body.getAllElements();
		boolean tag = false;
		for (Element e : es) {
			//跳过第一个（默认会把整个对象当做第一个）
			if(!tag) {
				tag = true;
				continue;
			}
			//创建段落：生成word（核心）
			createXWPFParagraph(docxDocument,e);
		}
		
		//设置页边距
		//setDocumentMargin(docxDocument, (int)(WordUtil.ONE_UNIT*2.5)+"", (WordUtil.ONE_UNIT*2)+"", (int)(WordUtil.ONE_UNIT*2.5)+"", (WordUtil.ONE_UNIT*2)+"");
		
		if(StringUtils.isNotBlank(pageHeader)){
			//设置页眉
			createDefaultHeader(docxDocument, pageHeader);
		}
		
		if(pageFooter){
			//设置页脚（页码）
			createDefaultFooter(docxDocument);
		}
		
		// word写入到文件
	    FileOutputStream out = new FileOutputStream(path+filename+".docx");
	    docxDocument.write(out);
	    out.close();
	    System.out.println("创建完成");
	    
		return path;
    }
	
	
	/**
	 * 增加自定义标题样式。这里用的是stackoverflow的源码
	 * 
	 * @param docxDocument 目标文档
	 * @param strStyleId 样式名称
	 * @param headingLevel 样式级别
	 */
	private static void addCustomHeadingStyle(XWPFDocument docxDocument, String strStyleId, int headingLevel) {

	    CTStyle ctStyle = CTStyle.Factory.newInstance();
	    ctStyle.setStyleId(strStyleId);

	    CTString styleName = CTString.Factory.newInstance();
	    styleName.setVal(strStyleId);
	    ctStyle.setName(styleName);

	    CTDecimalNumber indentNumber = CTDecimalNumber.Factory.newInstance();
	    indentNumber.setVal(BigInteger.valueOf(headingLevel));

	    // lower number > style is more prominent in the formats bar
	    ctStyle.setUiPriority(indentNumber);

	    CTOnOff onoffnull = CTOnOff.Factory.newInstance();
	    ctStyle.setUnhideWhenUsed(onoffnull);

	    // style shows up in the formats bar
	    ctStyle.setQFormat(onoffnull);

	    // style defines a heading of the given level
	    CTPPr ppr = CTPPr.Factory.newInstance();
	    ppr.setOutlineLvl(indentNumber);
	    ctStyle.setPPr(ppr);

	    XWPFStyle style = new XWPFStyle(ctStyle);

	    // is a null op if already defined
	    XWPFStyles styles = docxDocument.createStyles();

	    style.setType(STStyleType.PARAGRAPH);
	    styles.addStyle(style);

	}
	
	
	/**
	 * 构建段落
	 * @param docxDocument
	 * @param e
	 */
	public static void createXWPFParagraph(XWPFDocument docxDocument, Element e){
		XWPFParagraph paragraph = docxDocument.createParagraph();
	    XWPFRun run = paragraph.createRun();
	    run.setText(e.text());
	    run.setTextPosition(35);//设置行间距
	    
	    if(e.tagName().equals("titlename")){
	    	paragraph.setAlignment(ParagraphAlignment.CENTER);//对齐方式

	    	run.setBold(true);//加粗
		    run.setColor("000000");//设置颜色--十六进制
		    run.setFontFamily("宋体");//字体
		    run.setFontSize(24);//字体大小
		    
		}else if(e.tagName().equals("h1")){
		    addCustomHeadingStyle(docxDocument, "标题 1", 1);
		    paragraph.setStyle("标题 1");
		    
		    run.setBold(true);
		    run.setColor("000000");
		    run.setFontFamily("宋体");
		    run.setFontSize(20);
		}else if(e.tagName().equals("h2")){
			addCustomHeadingStyle(docxDocument, "标题 2", 2);
			paragraph.setStyle("标题 2");
			
			run.setBold(true);
		    run.setColor("000000");
		    run.setFontFamily("宋体");
		    run.setFontSize(18);
		}else if(e.tagName().equals("h3")){
			addCustomHeadingStyle(docxDocument, "标题 3", 3);
			paragraph.setStyle("标题 3");
			
			run.setBold(true);
		    run.setColor("000000");
		    run.setFontFamily("宋体");
		    run.setFontSize(16);
		}else if(e.tagName().equals("p")){
			//内容
			paragraph.setAlignment(ParagraphAlignment.BOTH);//对齐方式
		    paragraph.setIndentationFirstLine(WordUtil.ONE_UNIT);//首行缩进：567==1厘米
		    
			run.setBold(false);
		    run.setColor("001A35");
		    run.setFontFamily("宋体");
		    run.setFontSize(14);
			//run.addCarriageReturn();//回车键
		}else if(e.tagName().equals("break")){
			paragraph.setPageBreak(true);//段前分页(ctrl+enter)
		}
	}
	
	/*
	 * 设置页面大小及纸张方向 landscape横向
	 * @param document
	 * @param width
	 * @param height
	 * @param stValue
	 */
	/*public void setDocumentSize(XWPFDocument document, String width,String height, STPageOrientation.Enum stValue) {
		CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
		CTPageSz pgsz = sectPr.isSetPgSz() ? sectPr.getPgSz() : sectPr.addNewPgSz();
		pgsz.setH(new BigInteger(height));
		pgsz.setW(new BigInteger(width));
		pgsz.setOrient(stValue);
	}*/
	  
	  /*
	   * 设置页边距 (word中1厘米约等于567) 
	   * @param document
	   * @param left
	   * @param top
	   * @param right
	   * @param bottom
	   */
	  /*public static void setDocumentMargin(XWPFDocument document, String left,String top, String right, String bottom) {
	    CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
	    CTPageMar ctpagemar = sectPr.addNewPgMar();
	    if (StringUtils.isNotBlank(left)) {  
	      ctpagemar.setLeft(new BigInteger(left));  
	    }  
	    if (StringUtils.isNotBlank(top)) {  
	      ctpagemar.setTop(new BigInteger(top));  
	    }  
	    if (StringUtils.isNotBlank(right)) {  
	      ctpagemar.setRight(new BigInteger(right));  
	    }  
	    if (StringUtils.isNotBlank(bottom)) {  
	      ctpagemar.setBottom(new BigInteger(bottom));  
	    }  
	  } */
	  
	
	
	/*
     * 创建默认页眉
     *
     * @param docx XWPFDocument文档对象
     * @param text 页眉文本
     * @return 返回文档帮助类对象，可用于方法链调用
     * @throws XmlException XML异常
     * @throws IOException IO异常
     * @throws InvalidFormatException 非法格式异常
     * @throws FileNotFoundException 找不到文件异常
     */
    public static void createDefaultHeader(final XWPFDocument docx, final String text){
        CTP ctp = CTP.Factory.newInstance();
        XWPFParagraph paragraph = new XWPFParagraph(ctp, docx);
        ctp.addNewR().addNewT().setStringValue(text);
        ctp.addNewR().addNewT().setSpace(SpaceAttribute.Space.PRESERVE);
        CTSectPr sectPr = docx.getDocument().getBody().isSetSectPr() ? docx.getDocument().getBody().getSectPr() : docx.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(docx, sectPr);
        XWPFHeader header = policy.createHeader(STHdrFtr.DEFAULT, new XWPFParagraph[] { paragraph });
        header.setXWPFDocument(docx);
    }
    
    /*
	 * 创建默认的页脚(该页脚主要只居中显示页码)
	 * 
	 * @param docx
	 *            XWPFDocument文档对象
	 * @return 返回文档帮助类对象，可用于方法链调用
	 * @throws XmlException
	 *             XML异常
	 * @throws IOException
	 *             IO异常
	 */
	public static void createDefaultFooter(final XWPFDocument docx) {
		// TODO 设置页码起始值
		CTP pageNo = CTP.Factory.newInstance();
		XWPFParagraph footer = new XWPFParagraph(pageNo, docx);
		CTPPr begin = pageNo.addNewPPr();
		begin.addNewPStyle().setVal(STYLE_FOOTER);
		begin.addNewJc().setVal(STJc.CENTER);
		pageNo.addNewR().addNewFldChar().setFldCharType(STFldCharType.BEGIN);
		pageNo.addNewR().addNewInstrText().setStringValue("PAGE   \\* MERGEFORMAT");
		pageNo.addNewR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);
		CTR end = pageNo.addNewR();
		CTRPr endRPr = end.addNewRPr();
		endRPr.addNewNoProof();
		endRPr.addNewLang().setVal(LANG_ZH_CN);
		end.addNewFldChar().setFldCharType(STFldCharType.END);
		CTSectPr sectPr = docx.getDocument().getBody().isSetSectPr() ? docx.getDocument().getBody().getSectPr() : docx.getDocument().getBody().addNewSectPr();
		XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(docx, sectPr);
		policy.createFooter(STHdrFtr.DEFAULT, new XWPFParagraph[] { footer });
	}

}

