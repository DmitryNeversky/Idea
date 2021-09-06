import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {IdeasResolver} from "./resolvers/ideas.resolver";
import {IdeasComponent} from "./templates/ideas/ideas.component";
import {CreateIdeaComponent} from "./templates/create-idea/create-idea.component";
import {IdeaFillComponent} from "./templates/idea-fill/idea-fill.component";
import {IdeaResolver} from "./resolvers/idea.resolver";
import {HomeComponent} from "./templates/home/home.component";
import {TagResolver} from "./resolvers/tag.resolver";
import {UpdateIdeaComponent} from "./templates/update-idea/update-idea.component";
import {AuthGuard} from "./security/auth.guard";
import {LoginComponent} from "./templates/login/login.component";
import {RegistrationComponent} from "./templates/registration/registration.component";
import {UserComponent} from "./templates/user/user.component";
import {UserResolver} from "./resolvers/user.resolver";
import {CurrentUserResolver} from "./resolvers/current-user.resolver";
import {SettingsComponent} from "./templates/settings/settings.component";
import {ProfileComponent} from "./templates/settings/profile/profile.component";
import {PostsResolver} from "./resolvers/posts.resolver";
import {NotifiesComponent} from "./templates/settings/notifies/notifies.component";
import {InterfaceComponent} from "./templates/settings/interface/interface.component";
import {SecureComponent} from "./templates/settings/secure/secure.component";
import {PathGuard} from "./security/path.guard";
import {UserProfileComponent} from "./templates/user/profile/user-profile.component";
import {UserIdeasComponent} from "./templates/user/ideas/user-ideas.component";
import {UserContactsComponent} from "./templates/user/contacts/user-contacts.component";

const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
        data: { animation: 'home' },
        canActivate: [AuthGuard],
        resolve: {
            currentUser: CurrentUserResolver
        },
        children: [
            {
                path: 'ideas',
                component: IdeasComponent,
                data: { animation: 'ideas' },
                canActivate: [AuthGuard],
                resolve: {
                    ideas: IdeasResolver,
                    currentUser: CurrentUserResolver
                },
            },
            {
                path: 'ideas/id/:id',
                component: IdeaFillComponent,
                data: { animation: 'idea' },
                canActivate: [AuthGuard],
                resolve: {
                    idea: IdeaResolver,
                    currentUser: CurrentUserResolver
                }
            },
            {
                path: 'ideas/create',
                component: CreateIdeaComponent,
                data: { animation: 'idea-create' },
                canActivate: [AuthGuard],
                resolve: {
                    tags: TagResolver
                }
            },
            {
                path: 'ideas/update/:id',
                component: UpdateIdeaComponent,
                data: { animation: 'update-idea' },
                canActivate: [AuthGuard],
                resolve: {
                    idea: IdeaResolver
                }
            },
            {
                path: 'profile',
                component: UserComponent,
                data: { animation: 'profile' },
                canActivate: [AuthGuard],
                resolve: {
                    currentUser: CurrentUserResolver,
                },
            },
            {
                path: 'settings',
                component: SettingsComponent,
                data: { animation: 'settings' },
                canActivate: [AuthGuard],
                resolve: {
                    currentUser: CurrentUserResolver
                },
                children: [
                    {
                        path: '',
                        pathMatch: 'full',
                        redirectTo: 'profile'
                    },
                    {
                        path: 'profile',
                        component: ProfileComponent,
                        data: { animation: 'profile' },
                        resolve: {
                            currentUser: CurrentUserResolver,
                            posts: PostsResolver,
                        }
                    },
                    {
                        path: 'notifies',
                        component: NotifiesComponent,
                        data: { animation: 'notifies' },
                        resolve: {
                            currentUser: CurrentUserResolver,
                        }
                    },
                    {
                        path: 'interface',
                        component: InterfaceComponent,
                        data: { animation: 'interface' },
                        resolve: {
                            currentUser: CurrentUserResolver,
                        }
                    },
                    {
                        path: 'secure',
                        component: SecureComponent,
                        data: { animation: 'secure' },
                        resolve: {
                            currentUser: CurrentUserResolver,
                        }
                    }
                ]
            },
            {
                path: 'user/:id',
                component: UserComponent,
                data: { animation: 'user' },
                canActivate: [AuthGuard],
                children: [
                    {
                        path: '',
                        pathMatch: 'full',
                        redirectTo: 'profile'
                    },
                    {
                        path: 'profile',
                        component: UserProfileComponent,
                        data: { animation: 'profile' },
                        resolve: {
                            user: UserResolver
                        }
                    },
                    {
                        path: 'ideas',
                        component: UserIdeasComponent,
                        data: { animation: 'ideas' },
                        resolve: {
                            user: UserResolver
                        }
                    },
                    {
                        path: 'contacts',
                        component: UserContactsComponent,
                        data: { animation: 'contacts' },
                        resolve: {
                            user: UserResolver
                        }
                    }
                ]
            },
        ]
    },
    {
        path: 'auth',
        component: LoginComponent,
        data: { animation: 'auth' },
        // canActivate: [PathGuard],
    },
    {
        path: 'registration',
        component: RegistrationComponent,
        data: { animation: 'registration' },
        // canActivate: [PathGuard],
        resolve: {
            posts: PostsResolver
        }
    },
]

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {

}