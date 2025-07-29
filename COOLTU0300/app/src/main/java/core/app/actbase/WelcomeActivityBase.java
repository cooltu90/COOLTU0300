package core.app.actbase;
    protected android.view.ViewGroup rootViewGroup;

public abstract class WelcomeActivityBase<T extends com.codingtu.cooltu.lib.data.User, F>
        extends com.codingtu.cooltu.lib.ui.page.BaseWelcomeActivity<T, java.lang.String, F> {

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
    }

    private int getLayout() {
        return com.codingtu.cooltu.R.layout.activity_welcome;
    }

}
