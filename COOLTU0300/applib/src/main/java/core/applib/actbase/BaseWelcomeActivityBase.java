package core.applib.actbase;

public abstract class BaseWelcomeActivityBase
        extends com.codingtu.cooltu.lib.ui.page.BaseActivity {

    public String baseClassName = "BaseWelcomeActivityBase";

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String nowBaseClassName = getClass().getSimpleName() + "Base";
        if (nowBaseClassName.equals(baseClassName)) {
            onCreateComplete();
        }
    }

}
