package net.rubicon.indepth;

import android.content.Context;
import android.location.Location;

import net.rubicon.indepth.chain.CommandEnum;
import net.rubicon.indepth.chain.CommandFactory;
import net.rubicon.indepth.chain.ContextFactory;
import net.rubicon.indepth.chain.ContextList;
import net.rubicon.indepth.chain.UpdateCtx;
import net.rubicon.indepth.db.DataBaseModel;
import net.rubicon.indepth.db.PersonModel;
import net.rubicon.indepth.db.StatusEnum;
import net.rubicon.indepth.db.StrikeTeamModel;
import net.rubicon.indepth.db.TeamModel;

import java.util.List;

/**
 * Seed the database for various scenarios
 */
public class DataBaseScenario {





    public void loadTeam(Context context) {
        String names[] = {
                "Stuart",
                "Donal",
                "Perry",
                "Jonas"
        };

        double locations[][] = {
                {37.7837261, -122.3960743},
                {37.782319, -122.410485},
                {37.788865, -122.400895},
                {37.787220, -122.398819}
        };

        StatusEnum status[] = {
                StatusEnum.NORMAL,
                StatusEnum.NORMAL,
                StatusEnum.SERIOUS,
                StatusEnum.GUARDED,
                StatusEnum.CRITICAL
        };


        for (int i = 0; i < names.length; i++) {
            TeamModel model = new TeamModel();
            model.setDefault();

            model.setName(names[i]);


            Location location = new Location(names[i]);
            location.setLatitude(locations[i][0]);
            location.setLongitude(locations[i][1]);

            model.setLocation(location);

            model.setStatus(status[i]);

            ContextList list = createContextList(CommandEnum.TEAM_UPDATE, model, context);

            CommandFactory.execute(list);
        }


    }


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
                "heavy equipment, sawyer"
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
            model.setStatus(status[i]);
            model.setTeam(teams[i]);


            ContextList list = createContextList(CommandEnum.PERSON_UPDATE, model, context);

            CommandFactory.execute(list);
        }
    }
}


