package core.app.actbase;

public abstract class WelcomeActivityBase<T extends com.codingtu.cooltu.lib.data.User, F>
        extends com.codingtu.cooltu.lib.ui.page.BaseWelcomeActivity<T, java.lang.String, F>
        implements android.view.View.OnClickListener {

    protected android.view.ViewGroup rootViewGroup;
    protected android.widget.LinearLayout ll0;
    protected android.widget.RelativeLayout rl0;
    protected android.widget.TextView tv0;
    protected android.widget.RelativeLayout rl1;
    protected android.widget.TextView tv1;
    protected android.widget.LinearLayout ll1;
    protected android.widget.RelativeLayout rl2;
    protected android.widget.TextView tv2;
    protected android.widget.RelativeLayout rl3;
    protected android.widget.TextView tv3;
    protected android.widget.TextView tag_0;
    public String baseClassName = "WelcomeActivityBase";

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        rootViewGroup = com.codingtu.cooltu.lib4a.tools.ViewTool.getRootViewGroup(this);
        ll0 = (android.widget.LinearLayout) rootViewGroup.getChildAt(0);
        rl0 = (android.widget.RelativeLayout) ll0.getChildAt(0);
        tv0 = (android.widget.TextView) rl0.getChildAt(0);
        rl1 = (android.widget.RelativeLayout) ll0.getChildAt(1);
        tv1 = (android.widget.TextView) rl1.getChildAt(0);
        ll1 = (android.widget.LinearLayout) rootViewGroup.getChildAt(1);
        rl2 = (android.widget.RelativeLayout) ll1.getChildAt(0);
        tv2 = (android.widget.TextView) rl2.getChildAt(0);
        rl3 = (android.widget.RelativeLayout) ll1.getChildAt(1);
        tv3 = (android.widget.TextView) rl3.getChildAt(0);
        tag_0 = (android.widget.TextView) rootViewGroup.getChildAt(2);
        String nowBaseClassName = getClass().getSimpleName() + "Base";
        if (nowBaseClassName.equals(baseClassName)) {
            onCreateComplete();
        }
    }

    private int getLayout() {
        return com.codingtu.cooltu.R.layout.activity_welcome;
    }

    @Override
    public void onClick(android.view.View v) {
        super.onClick(v);
        try {
            switch (v.getId()) {
                case com.codingtu.cooltu.R.id.tv:
                case com.codingtu.cooltu.R.id.ll:
                    tvClick(
                            v
                            , (com.codingtu.cooltu.lib.data.User) v.getTag(com.codingtu.cooltu.lib4a.R.id.tag_0)
                            , v
                    );
                    break;
            }
        } catch (Exception e) {
            com.codingtu.cooltu.lib4a.log.Logs.e(e);
            if (!(e instanceof com.codingtu.cooltu.lib4a.exception.NotToastException)) {
                toast(e.getMessage());
            }
        }
    }

    public void tvClick(android.view.View view, com.codingtu.cooltu.lib.data.User user, android.view.View view1) {
    }

}
