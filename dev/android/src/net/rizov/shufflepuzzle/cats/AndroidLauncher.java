package net.rizov.shufflepuzzle.cats;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import net.rizov.gameutils.scene.Factory;
import net.rizov.gameutils.scene.Game;
import net.rizov.shufflepuzzle.ShufflePuzzle;
import net.rizov.shufflepuzzle.room.menu.input.MenuRoomInputProcessor;
import net.rizov.shufflepuzzle.room.menu.input.TouchScreenMenuRoomInputProcessor;
import net.rizov.shufflepuzzle.room.play.challenge.input.ChallengePlayRoomInputProcessor;
import net.rizov.shufflepuzzle.room.play.timetrial.input.TimeTrialPlayRoomInputProcessor;
import net.rizov.shufflepuzzle.room.play.timetrial.input.TouchScreenPlayRoomInputProcessor;
import net.rizov.shufflepuzzle.utils.LinkProvider;
import net.rizov.shufflepuzzle.utils.PackList;
import net.rizov.shufflepuzzle.utils.config.ConfigManager;
import net.rizov.shufflepuzzle.utils.config.ConfigNameProvider;
import net.rizov.shufflepuzzle.utils.helper.Base64;
import net.rizov.shufflepuzzle.utils.helper.CryptHelper;
import net.rizov.shufflepuzzle.utils.save.GdxPreferencesSaveConnector;
import net.rizov.shufflepuzzle.utils.save.SaveConnector;
import net.rizov.shufflepuzzle.utils.save.SaveManager;
import net.rizov.shufflepuzzle.utils.save.SaveNameProvider;

public class AndroidLauncher extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        final Game game = new Game();

        final ShufflePuzzle shufflePuzzle = new ShufflePuzzle(game);
        initialize(shufflePuzzle, config);

        game.addFactory(TimeTrialPlayRoomInputProcessor.class, new Factory<TimeTrialPlayRoomInputProcessor>() {
            @Override
            public TimeTrialPlayRoomInputProcessor create() {
                TimeTrialPlayRoomInputProcessor inputProcessor = new TouchScreenPlayRoomInputProcessor();
                return inputProcessor;
            }
        });

        game.addFactory(ChallengePlayRoomInputProcessor.class, new Factory<ChallengePlayRoomInputProcessor>() {
            @Override
            public ChallengePlayRoomInputProcessor create() {
                ChallengePlayRoomInputProcessor inputProcessor = new net.rizov.shufflepuzzle.room.play.challenge.input.TouchScreenPlayRoomInputProcessor();
                return inputProcessor;
            }
        });

        game.addFactory(MenuRoomInputProcessor.class, new Factory<MenuRoomInputProcessor>() {
            @Override
            public MenuRoomInputProcessor create() {
                MenuRoomInputProcessor inputProcessor = new TouchScreenMenuRoomInputProcessor();
                return inputProcessor;
            }
        });

        game.addFactory(PackList.class, new Factory<PackList>() {
            @Override
            public PackList create() {
                return new net.rizov.shufflepuzzle.cats.AndroidPackList();
            }
        });

        game.addFactory(ConfigNameProvider.class, new Factory<ConfigNameProvider>() {
            @Override
            public ConfigNameProvider create() {
                return new ConfigNameProvider() {
                    @Override
                    public String getName() {
                        return "net.rizov.shufflepuzzle.cats.config";
                    }
                };
            }
        });

        game.addFactory(SaveNameProvider.class, new Factory<SaveNameProvider>() {
            @Override
            public SaveNameProvider create() {
                return new SaveNameProvider() {
                    @Override
                    public String getName() {
                        return "net.rizov.shufflepuzzle.cats.save";
                    }
                };
            }
        });

        game.addFactory(SaveConnector.class, new Factory<SaveConnector>() {
            @Override
            public SaveConnector create() {
                return new GdxPreferencesSaveConnector(game);
            }
        });

        game.addFactory(Base64.class, new Factory<Base64>() {
            @Override
            public Base64 create() {
                return new Base64() {
                    @Override
                    public String encode(byte[] data) {
                        return android.util.Base64.encodeToString(data, 0);
                    }

                    @Override
                    public byte[] decode(String data) {
                        return android.util.Base64.decode(data, 0);
                    }
                };
            }
        });

        game.addFactory(LinkProvider.class, new Factory<LinkProvider>() {
            @Override
            public LinkProvider create() {
                return new LinkProvider() {
                    @Override
                    public String getMoreLink() {
                        return "https://github.com/deianvn/shufflepuzzle";
                    }

                    @Override
                    public String getGameLink() {
                        return "https://github.com/deianvn/shufflepuzzle";
                    }
                };
            }
        });

        CryptHelper.injectMembers(game);

        ConfigManager.create(game);
        ConfigManager.load();

        SaveManager.create(game);
        SaveManager.load();

    }
}
