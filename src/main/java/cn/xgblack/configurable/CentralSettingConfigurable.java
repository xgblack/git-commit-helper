package cn.xgblack.configurable;

import cn.xgblack.storage.GitCommitMessageHelperSettings;
import cn.xgblack.ui.central.CentralSettingPanel;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * 中央设置配置类
 *
 * @author xg black
 */

public class CentralSettingConfigurable implements SearchableConfigurable {

    private CentralSettingPanel centralSettingPanel;

    private GitCommitMessageHelperSettings settings;

    public CentralSettingConfigurable() {
        settings = ServiceManager.getService(GitCommitMessageHelperSettings.class);
    }

    @Override
    public @NotNull @NonNls String getId() {
        return "plugins.gitcommitmessagehelper";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (centralSettingPanel == null) {
            centralSettingPanel = new CentralSettingPanel(settings);
        }
        return centralSettingPanel.getMainPanel();
    }

    @Override
    public void reset() {
        centralSettingPanel.reset(settings);
    }

    @Override
    public boolean isModified() {
        return centralSettingPanel.isModified(settings);
    }

    @Override
    public void apply() {
        settings.setCentralSettings(centralSettingPanel.getSettings().getCentralSettings());
        settings = centralSettingPanel.getSettings().clone();
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "GitCommitMessageHelper";
    }
}
