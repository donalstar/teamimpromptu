package net.teamimpromptu.fieldmanager.db;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import net.teamimpromptu.fieldmanager.chain.CommandEnum;
import net.teamimpromptu.fieldmanager.service.Member;
import net.teamimpromptu.fieldmanager.service.ServerFacade;

import java.util.List;
import java.util.Map;

/**
 * Created by donal on 4/4/16.
 */
public class DatabaseSync {
    public static final String LOG_TAG = DatabaseSync.class.getName();

    private static ContentFacade _contentFacade = new ContentFacade();
    private static ServerFacade _serverFacade = ServerFacade.getInstance();

    /**
     * @param context
     */
    public static void sync(Context context) {
        syncMembers(context);
    }

    private static void syncMembers(final Context context) {
        Log.i(LOG_TAG, "syncMembers");

        _serverFacade.getTeamMembers(new ServerFacade.ServerFacadeCallback() {
            @Override
            public void onFailure(String status) {
                Log.e(LOG_TAG, "Failed to get members: " + status);
            }

            @Override
            public void onSuccess(Map<String, Object> attributes) {
                List<Member> members = (List<Member>) attributes.get("members");

                for (Member member : members) {
                    Log.i(LOG_TAG, "Got member :" + member);
                }

                updateMembersInLocalDatabase(members, context);
            }
        });
    }

    private static void updateMembersInLocalDatabase(List<Member> members, Context context) {
        for (Member member : members) {
            if (_contentFacade.selectPersonByServerId(member.getId(), context) == null) {
                updateMemberInDatabase(member, context);
            }
        }
    }

    /**
     * @param member
     * @param context
     */
    private static void updateMemberInDatabase(Member member,
                                               final Context context) {

        PersonModel model = new PersonModel();
        model.setDefault();

        model.setName(member.getName());
        model.setServerId((long) member.getId());
        model.setUsername(member.getName().toLowerCase());
        model.setTeam(member.getTeam());


        Location location = new Location(member.getName());

        if (member.getLatitude() != null && member.getLongitude() != null) {
            location.setLatitude(Double.valueOf(member.getLatitude()));
            location.setLongitude(Double.valueOf(member.getLongitude()));
        }

        model.setLocation(location);

        _contentFacade.updateModel(CommandEnum.PERSON_UPDATE, model, context);
    }
}
