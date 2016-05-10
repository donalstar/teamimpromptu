package net.teamimpromptu.fieldmanager;

import android.content.Context;
import android.location.Location;

import net.teamimpromptu.fieldmanager.chain.CommandEnum;
import net.teamimpromptu.fieldmanager.chain.CommandFactory;
import net.teamimpromptu.fieldmanager.chain.ContextFactory;
import net.teamimpromptu.fieldmanager.chain.ContextList;
import net.teamimpromptu.fieldmanager.chain.UpdateCtx;
import net.teamimpromptu.fieldmanager.db.DataBaseModel;
import net.teamimpromptu.fieldmanager.db.PersonModel;
import net.teamimpromptu.fieldmanager.db.StatusEnum;
import net.teamimpromptu.fieldmanager.db.StrikeTeamModel;

/**
 * Seed the database for various scenarios
 */
public class DataBaseScenario {


    private ContextList createContextList(CommandEnum command, DataBaseModel model, Context context) {
        ContextList list = new ContextList();

        UpdateCtx ctx = (UpdateCtx) ContextFactory.factory(command, context);
        ctx.setModel(model);
        list.add(ctx);

        return list;
    }

    public void loadStrikeTeams(Context context) {
        String names[] = {
                "Alpha",
                "Bravo",
                "Gamma"
        };

        double locations[][] = {
                {37.7837261, -122.3960743},
                {37.782319, -122.410485},
                {37.787220, -122.398819}
        };

        StatusEnum status[] = {
                StatusEnum.NORMAL,
                StatusEnum.NORMAL,
                StatusEnum.CRITICAL
        };


        for (int i = 0; i < names.length; i++) {
            StrikeTeamModel model = new StrikeTeamModel();
            model.setDefault();

            model.setName(names[i]);


            Location location = new Location(names[i]);
            location.setLatitude(locations[i][0]);
            location.setLongitude(locations[i][1]);

            model.setLocation(location);

            model.setStatus(status[i]);

            ContextList list = createContextList(CommandEnum.STRIKE_TEAM_UPDATE, model, context);

            CommandFactory.execute(list);
        }


    }

    public void loadPersons(Context context) {
        String names[] = {
                "Donal"
        };


        String usernames[] = {
                "donal"
        };

        String roles[] = {
                "team_leader"
        };

        String skills[] = {
                "doctor"
        };

        String certifications[] = {
                "MD"
        };

        String status[] = {
                "active"
        };

        String teams[] = {
                "Alpha"
        };

        for (int i = 0; i < names.length; i++) {
            PersonModel model = new PersonModel();
            model.setDefault();

            model.setName(names[i]);
            model.setUsername(usernames[i]);
            model.setRole(roles[i]);
            model.setSkills(skills[i]);
            model.setCertifications(certifications[i]);
            model.setStatus(status[i]);
            model.setTeam(teams[i]);


            ContextList list = createContextList(CommandEnum.PERSON_UPDATE, model, context);

            CommandFactory.execute(list);
        }
    }
}


